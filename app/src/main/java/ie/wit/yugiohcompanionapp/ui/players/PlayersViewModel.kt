package ie.wit.yugiohcompanionapp.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.yugiohcompanionapp.firebase.FirebaseDBManager
import ie.wit.yugiohcompanionapp.models.PlayerModel
import timber.log.Timber

class PlayersViewModel : ViewModel() {
    private val playersList = MutableLiveData<List<PlayerModel>>()
    var readOnly = MutableLiveData(false)

    val observablePlayersList: LiveData<List<PlayerModel>>
        get() = playersList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, playersList)
            Timber.i("Players Load Success : ${playersList.value.toString()}")
        } catch (e: Exception) {
            Timber.i("Players Load Failure: ${e.message}")
        }
    }

    fun delete(userid: String, id: String){
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Player Deleted")
        } catch (e:Exception){
            Timber.i("Error ${e.message}")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(playersList)
            Timber.i("Report LoadAll Success : ${playersList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

}