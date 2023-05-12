package ie.wit.yugiohcompanionapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.yugiohcompanionapp.firebase.FirebaseDBManager
import ie.wit.yugiohcompanionapp.firebase.FirebaseImageManager
import ie.wit.yugiohcompanionapp.models.PlayerModel

class AddPlayersViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addPlayer(firebaseUser: MutableLiveData<FirebaseUser>,
                  player: PlayerModel
    ) {
        status.value = try {
            player.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser, player)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}