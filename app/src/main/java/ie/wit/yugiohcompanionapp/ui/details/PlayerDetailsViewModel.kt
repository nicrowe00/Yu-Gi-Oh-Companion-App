package ie.wit.yugiohcompanionapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.yugiohcompanionapp.firebase.FirebaseDBManager
import ie.wit.yugiohcompanionapp.models.PlayerModel
import timber.log.Timber

class PlayerDetailsViewModel : ViewModel() {
    private val player = MutableLiveData<PlayerModel>()
    var readOnly = MutableLiveData(false)

    var observablePlayer: LiveData<PlayerModel>
        get() = player
        set(value) {player.value = value.value}

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    fun getPlayer(userid:String, id: String) {
        try {
            FirebaseDBManager.findByID(userid, id, player)
        }
        catch (e: Exception) {
        }
    }

    fun updatePlayer(userid:String, id: String,player: PlayerModel) {
        try {
            FirebaseDBManager.update(userid, id, player)
            Timber.i("Detail update() Success : $player")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }


}