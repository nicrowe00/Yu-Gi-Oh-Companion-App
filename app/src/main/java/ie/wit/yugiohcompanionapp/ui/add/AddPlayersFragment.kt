package ie.wit.yugiohcompanionapp.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.FragmentAddPlayersBinding
import ie.wit.yugiohcompanionapp.models.PlayerModel
import ie.wit.yugiohcompanionapp.ui.login.LoggedInViewModel

class AddPlayersFragment : Fragment() {
    private var _fragBinding: FragmentAddPlayersBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var addPlayersViewModel: AddPlayersViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentAddPlayersBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        activity?.title = "Add Player"
        addPlayersViewModel = ViewModelProvider(this).get(AddPlayersViewModel::class.java)
        addPlayersViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
            status -> status?.let{ render(status)}
        })
        setButtonListener(fragBinding)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.cancel -> {
                val action = AddPlayersFragmentDirections.actionAddPlayersFragmentToPlayersFragment()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setButtonListener(layout: FragmentAddPlayersBinding) {
        layout.save.setOnClickListener {
            val playername = if (layout.playername.text.isNotEmpty())
                layout.playername.text.toString() else "Guest"
            val playerwins = if (layout.playerwins.text.isNotEmpty())
                layout.playerwins.text.toString() else "0"
            val playermatches = if (layout.playermatches.text.isNotEmpty())
                layout.playermatches.text.toString() else "0"
            val playerduellinks = if (layout.playerduellinks.text.isNotEmpty())
                layout.playerduellinks.text.toString() else "N/A"
            val playermasterduel = if (layout.playermasterduel.text.isNotEmpty())
                layout.playermasterduel.text.toString() else "N/A"
            addPlayersViewModel.addPlayer(loggedInViewModel.liveFirebaseUser,
                PlayerModel(playername = playername, playerwins = playerwins, playermatches = playermatches, playerduellinks = playerduellinks, playermasterduel = playermasterduel, email = loggedInViewModel.liveFirebaseUser.value?.email!!)
            )
            val action = AddPlayersFragmentDirections.actionAddPlayersFragmentToPlayersFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {

            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_player_details, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               val action = AddPlayersFragmentDirections.actionAddPlayersFragmentToPlayersFragment()
                findNavController().navigate(action)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                }
            }
            false -> Toast.makeText(context,getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }
}