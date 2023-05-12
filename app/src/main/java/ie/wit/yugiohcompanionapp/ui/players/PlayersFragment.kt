package ie.wit.yugiohcompanionapp.ui.players

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.adapters.PlayerAdapter
import ie.wit.yugiohcompanionapp.adapters.PlayerListener
import ie.wit.yugiohcompanionapp.databinding.FragmentPlayersBinding
import ie.wit.yugiohcompanionapp.main.YugiohCompanion
import ie.wit.yugiohcompanionapp.models.PlayerModel
import ie.wit.yugiohcompanionapp.ui.add.AddPlayersFragment
import ie.wit.yugiohcompanionapp.ui.add.AddPlayersFragmentDirections
import ie.wit.yugiohcompanionapp.ui.login.LoggedInViewModel
import ie.wit.yugiohcompanionapp.utils.SwipeToDeleteCallback
import ie.wit.yugiohcompanionapp.utils.createLoader
import ie.wit.yugiohcompanionapp.utils.hideLoader
import ie.wit.yugiohcompanionapp.utils.showLoader
import timber.log.Timber

class PlayersFragment : Fragment(), PlayerListener{
    private var _fragBinding: FragmentPlayersBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val playersViewModel: PlayersViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentPlayersBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        showLoader(loader, "Retrieving Players...")
        playersViewModel.observablePlayersList.observe(viewLifecycleOwner, Observer {
            players ->
            players?.let {
                render(players as ArrayList<PlayerModel>)
                hideLoader(loader)
            }
        })

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Player")
                val adapter = fragBinding.recyclerView.adapter as PlayerAdapter
                adapter.removeAt(viewHolder.bindingAdapterPosition)
                playersViewModel.delete(
                    playersViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as PlayerModel).uid!!)

                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }
    private fun render(playersList: ArrayList<PlayerModel>) {
        fragBinding.recyclerView.adapter = PlayerAdapter(playersList,this, playersViewModel.readOnly.value!!)
        if (playersList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.playersNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.playersNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Retrieving Players...")
       loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
           if (firebaseUser != null){
               playersViewModel.liveFirebaseUser.value = firebaseUser
               playersViewModel.load()
           }
       })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onPlayerClick(player: PlayerModel) {
        val action = PlayersFragmentDirections.actionPlayersFragmentToPlayerDetailsFragment(player.uid!!)
        if(!playersViewModel.readOnly.value!!)
            findNavController().navigate(action)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_players, menu)

                val item = menu.findItem(R.id.togglePlayers) as MenuItem
                item.setActionView(R.layout.togglebutton_layout)
                val togglePlayers: SwitchCompat = item.actionView!!.findViewById(R.id.toggleButton)
                togglePlayers.isChecked = false

                togglePlayers.setOnCheckedChangeListener{ _, isChecked ->
                    if (isChecked) playersViewModel.loadAll()
                    else playersViewModel.load()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addplayer -> {
                        val action =
                            PlayersFragmentDirections.actionPlayersFragmentToAddPlayersFragment()
                        findNavController().navigate(action)
                    }
                }
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}
}