package ie.wit.yugiohcompanionapp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerModel(
    var uid: String? = "",
    var playername: String = "N/A",
    var playerwins: String = "N/A",
    var playermatches: String = "N/A",
    var playerduellinks: String = "N/A",
    var playermasterduel: String = "N/A",
    var `profilepic`: String = "",
    var email: String? = "joey@wheeler.com"
) : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "playername" to playername,
            "playerwins" to playerwins,
            "playermatches" to playermatches,
            "playerduellinks" to playerduellinks,
            "playermasterduel" to playermasterduel,
            "profilepic" to `profilepic`,
            "email" to email
        )
    }
}