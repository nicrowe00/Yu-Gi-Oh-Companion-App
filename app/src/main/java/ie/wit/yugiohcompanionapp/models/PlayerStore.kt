package ie.wit.yugiohcompanionapp.models

interface PlayerStore {
    fun findAll(): List<PlayerModel>
    fun findByName(playername:String) : PlayerModel?
    fun create(player: PlayerModel)
    fun update(player: PlayerModel)
    fun delete(player: PlayerModel)
    fun deleteAll()
}