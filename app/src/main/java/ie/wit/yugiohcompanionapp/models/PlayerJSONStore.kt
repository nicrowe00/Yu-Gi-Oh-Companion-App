package ie.wit.yugiohcompanionapp.models

import android.net.Uri
import android.content.Context
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import ie.wit.yugiohcompanionapp.helpers.*

fun generateRandomId(): Long {
    return Random().nextLong()
}

const val JSON_FILE = "players.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<PlayerModel>>() {}.type

class PlayerJSONStore(private val context: Context) : PlayerStore {

    var players = mutableListOf<PlayerModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun findByName(playername: String): PlayerModel? {
        val foundPlayer: PlayerModel? = players.find { it.playername == playername }
        return foundPlayer
    }

    override fun create(player: PlayerModel) {
        player.id = generateRandomId()
        players.add(player)
        serialize()
    }

    override fun update(player: PlayerModel) {
        val playersList = findAll() as ArrayList<PlayerModel>
        val foundPlayer: PlayerModel? = playersList.find { p -> p.id == player.id }
        if (foundPlayer != null) {
            foundPlayer.playername = player.playername
            foundPlayer.playerwins = player.playerwins
            foundPlayer.playermatches = player.playermatches
            foundPlayer.playerduellinks = player.playerduellinks
            foundPlayer.playermasterduel = player.playermasterduel
        }
        serialize()
    }

    override fun delete(player: PlayerModel) {
        players.remove(player)
        serialize()
    }

    override fun deleteAll() {
        players.removeAll(players.toSet())
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(players, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        players = gsonBuilder.fromJson(jsonString, listType)
    }
}

    class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Uri {
            return Uri.parse(json?.asString)
        }

        override fun serialize(
            src: Uri?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.toString())
        }
}