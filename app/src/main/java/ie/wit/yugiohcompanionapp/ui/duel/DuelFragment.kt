package ie.wit.yugiohcompanionapp.ui.duel

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.FragmentDuelBinding
import ie.wit.yugiohcompanionapp.main.YugiohCompanion

class DuelFragment : Fragment() {
    lateinit var app: YugiohCompanion
    private var _fragBinding: FragmentDuelBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as YugiohCompanion
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDuelBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        fragBinding.onePlayerButton.setOnClickListener(){
            val action = DuelFragmentDirections.actionDuelFragmentToDuelOnePlayerFragment()
            findNavController().navigate(action)
        }

        fragBinding.twoPlayerButton.setOnClickListener(){
            val action = DuelFragmentDirections.actionDuelFragmentToDuelTwoPlayerFragment()
            findNavController().navigate(action)
        }
        return root;
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _fragBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DuelFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}