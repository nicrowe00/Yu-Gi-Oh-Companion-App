package ie.wit.yugiohcompanionapp.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface PlayerStore {
    fun findAll(playersList: MutableLiveData<List<PlayerModel>>)

    fun findAll(userid:String,playersList:MutableLiveData<List<PlayerModel>>)
    fun findByID(userid:String, playerid: String, player: MutableLiveData<PlayerModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, player: PlayerModel)
    fun delete(userid:String, playerid:String)
    fun update(userid:String, playerid: String, player: PlayerModel)
}