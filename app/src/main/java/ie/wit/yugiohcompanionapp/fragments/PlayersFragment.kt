package ie.wit.yugiohcompanionapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.activities.PlayerDetails
import ie.wit.yugiohcompanionapp.adapters.PlayerAdapter
import ie.wit.yugiohcompanionapp.adapters.PlayerListener
import ie.wit.yugiohcompanionapp.databinding.FragmentPlayersBinding
import ie.wit.yugiohcompanionapp.main.YugiohCompanion
import ie.wit.yugiohcompanionapp.models.PlayerModel

class PlayersFragment : Fragment(), PlayerListener{
    lateinit var app: YugiohCompanion
    private var _fragBinding: FragmentPlayersBinding? = null
    private val fragBinding get() = _fragBinding!!
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as YugiohCompanion
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPlayersBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        fragBinding.recyclerView.layoutManager = layoutManager
        fragBinding.recyclerView.adapter = PlayerAdapter(app.players.findAll(), this)

        return root
    }



  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
      inflater.inflate(R.menu.menu_players, menu)
      super.onCreateOptionsMenu(menu, inflater)
  }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addplayer -> {
                val launcherIntent = Intent(activity?.applicationContext, PlayerDetails::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel, pos : Int){
        val launcherIntent = Intent(activity?.applicationContext, PlayerDetails::class.java)
        launcherIntent.putExtra("player_edit", player)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (fragBinding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.players.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)
                    (fragBinding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK) {
                (fragBinding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.players.findAll().size)
            }
        }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlayersFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}