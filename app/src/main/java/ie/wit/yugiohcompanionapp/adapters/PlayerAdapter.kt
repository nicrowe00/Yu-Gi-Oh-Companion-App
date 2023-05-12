package ie.wit.yugiohcompanionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.CardPlayerBinding
import ie.wit.yugiohcompanionapp.models.PlayerModel
import ie.wit.yugiohcompanionapp.utils.customTransformation

interface PlayerListener {

    fun onPlayerClick(player: PlayerModel)
}

class PlayerAdapter constructor(private var players: ArrayList<PlayerModel>,
private val listener: PlayerListener,
private val readOnly: Boolean)
: RecyclerView.Adapter<PlayerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlayerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val player = players[holder.adapterPosition]
        holder.bind(player,listener)
    }

    fun removeAt(position: Int) {
        players.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = players.size

    inner class MainHolder(val binding : CardPlayerBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(player: PlayerModel, listener: PlayerListener) {
            binding.playername.text = player.playername
            binding.playerwins.text = "Wins: " + player.playerwins
            binding.root.tag = player
            binding.player = player
            Picasso.get().load(player.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPlayerClick(player) }
            binding.executePendingBindings()
        }
    }
}
