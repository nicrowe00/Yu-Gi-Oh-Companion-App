package ie.wit.yugiohcompanionapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerModel(
    var id: Long = 0,
    var playername: String = "N/A",
    var playerwins: String = "N/A",
    var playermatches: String = "N/A",
    var playerduellinks: String = "N/A",
    var playermasterduel: String = "N/A"
) : Parcelable