package ie.wit.yugiohcompanionapp.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.yugiohcompanionapp.models.PlayerModel
import ie.wit.yugiohcompanionapp.models.PlayerStore
import timber.log.Timber

object FirebaseDBManager : PlayerStore{
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(playersList: MutableLiveData<List<PlayerModel>>) {
        database.child("players")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<PlayerModel>()
                    val children = snapshot.children
                    children.forEach {
                        val player = it.getValue(PlayerModel::class.java)
                        localList.add(player!!)
                    }
                    database.child("players")
                        .removeEventListener(this)

                    playersList.value = localList
                }
            })
    }

    override fun findAll(userid: String, playersList: MutableLiveData<List<PlayerModel>>) {
        database.child("user-players").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<PlayerModel>()
                    val children = snapshot.children
                    children.forEach {
                        val player = it.getValue(PlayerModel::class.java)
                        localList.add(player!!)
                    }
                    database.child("user-players").child(userid)
                        .removeEventListener(this)

                    playersList.value = localList
                }
            })
    }

    override fun findByID(userid: String, playerid: String, player: MutableLiveData<PlayerModel>) {
        database.child("user-players").child(userid)
            .child(playerid).get().addOnSuccessListener {
                player.value = it.getValue(PlayerModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, player: PlayerModel) {
        var id = firebaseUser.value!!.uid
        val key = database.child("players").push().key
        if (key == null){
            Timber.i("Firebase Error : Key Empty")
            return
        }
        player.uid = key
        val playerValues = player.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/players/$key"] = playerValues
        childAdd["/user-players/$id/$key"] = playerValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, playerid: String){
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/players/$playerid"] = null
        childDelete["/user-players/$userid/$playerid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, playerid: String, player: PlayerModel) {
        val playerValues = player.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["players/$playerid"] = playerValues
        childUpdate["user-players/$userid/$playerid"] = playerValues

        database.updateChildren(childUpdate)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userPlayers = database.child("user-players").child(userid)
        val allPlayers = database.child("players")

        userPlayers.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        it.ref.child("profilepic").setValue(imageUri)
                        val player = it.getValue(PlayerModel::class.java)
                        allPlayers.child(player!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }

}