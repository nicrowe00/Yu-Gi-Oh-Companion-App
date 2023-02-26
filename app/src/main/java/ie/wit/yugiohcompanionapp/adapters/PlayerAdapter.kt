package ie.wit.yugiohcompanionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.yugiohcompanionapp.databinding.CardPlayerBinding
import ie.wit.yugiohcompanionapp.models.PlayerModel

interface PlayerListener {

    fun onPlayerClick(player: PlayerModel, position: Int)
}

class PlayerAdapter constructor(private var players: List<PlayerModel>, private val listener: PlayerListener)
    : RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlayerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        holder.bind(player, listener)
    }

    override fun getItemCount(): Int = players.size

    inner class MainHolder(val binding : CardPlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerModel, listener: PlayerListener) {
            binding.playername.text = player.playername
            binding.playerwins.text = player.playerwins
            binding.root.setOnClickListener { listener.onPlayerClick(player,adapterPosition)}
        }
    }
}