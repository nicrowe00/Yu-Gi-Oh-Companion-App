package ie.wit.yugiohcompanionapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import ie.wit.yugiohcompanionapp.databinding.FragmentPlayerDetailsBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.models.PlayerModel
import ie.wit.yugiohcompanionapp.ui.login.LoggedInViewModel
import timber.log.Timber

class PlayerDetailsFragment : Fragment() {

    private lateinit var playerDetailsViewModel: PlayerDetailsViewModel
    private val args by navArgs<PlayerDetailsFragmentArgs>()
    private var _fragBinding: FragmentPlayerDetailsBinding? = null
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()

    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentPlayerDetailsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        playerDetailsViewModel = ViewModelProvider(this).get(PlayerDetailsViewModel::class.java)
        playerDetailsViewModel.observablePlayer.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.update.setOnClickListener(){
            playerDetailsViewModel.updatePlayer(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.playerid, fragBinding.playervm?.observablePlayer!!.value!!)
            findNavController().navigateUp()
        }


        return root

    }

    private fun render() {
        fragBinding.playervm = playerDetailsViewModel
    }

    override fun onResume() {
        super.onResume()
        playerDetailsViewModel.getPlayer(loggedInViewModel.liveFirebaseUser.value?.uid!!, args.playerid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}
