package ie.wit.yugiohcompanionapp.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.FragmentDueloneplayerBinding
import ie.wit.yugiohcompanionapp.main.YugiohCompanion

class DuelOnePlayerFragment : Fragment() {
    lateinit var app: YugiohCompanion
    private var _fragBinding: FragmentDueloneplayerBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as YugiohCompanion
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDueloneplayerBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        Chronometer()
        var pointsdrop = MediaPlayer.create(activity?.applicationContext, R.raw.points_drop)
        val lost: String = "Duel Lost"
        fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())


        fragBinding.button1.setOnClickListener() {
            var string = "1"
            fragBinding.textView1.append(string)
        }

        fragBinding.button2.setOnClickListener() {
            var string = "2"
            fragBinding.textView1.append(string)
        }

        fragBinding.button3.setOnClickListener() {
            var string = "3"
            fragBinding.textView1.append(string)
        }

        fragBinding.button4.setOnClickListener() {
            var string = "4"
            fragBinding.textView1.append(string)
        }

        fragBinding.button5.setOnClickListener() {
            var string = "5"
            fragBinding.textView1.append(string)
        }

        fragBinding.button6.setOnClickListener() {
            var string = "6"
            fragBinding.textView1.append(string)
        }

        fragBinding.button7.setOnClickListener() {
            var string = "7"
            fragBinding.textView1.append(string)
        }

        fragBinding.button8.setOnClickListener() {
            var string = "8"
            fragBinding.textView1.append(string)
        }

        fragBinding.button9.setOnClickListener() {
            var string = "9"
            fragBinding.textView1.append(string)
        }

        fragBinding.button0.setOnClickListener() {
            var string = "0"
            fragBinding.textView1.append(string)
        }

        fragBinding.button00.setOnClickListener() {
            var string = "00"
            fragBinding.textView1.append(string)
        }

        fragBinding.button000.setOnClickListener() {
            var string = "000"
            fragBinding.textView1.append(string)
        }

        fragBinding.buttonplus.setOnClickListener() {
            if (fragBinding.textView1.text == "") {
                val toast = Toast.makeText(
                    activity?.applicationContext, "You have to enter a number first!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (fragBinding.editTextNumber1.text == lost) {
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                var score = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                var points = Integer.parseInt(fragBinding.textView1.text.toString())
                var addition = score + points
                pointsdrop.start()
                Thread.sleep(2800)
                fragBinding.editTextNumber1.text = addition.toString()
                fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                if (Integer.parseInt(fragBinding.editTextNumber1.text.toString()) <= 0){
                    fragBinding.editTextNumber1.text = lost
                }
            }
        }

        fragBinding.buttonminus.setOnClickListener() {
            if (fragBinding.textView1.text == "") {
                val toast = Toast.makeText(
                    activity?.applicationContext, "You have to enter a number first!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (fragBinding.editTextNumber1.text == lost) {
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                var score = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                var points = Integer.parseInt(fragBinding.textView1.text.toString())
                var subtraction = score - points
                pointsdrop.start()
                Thread.sleep(2800)
                fragBinding.editTextNumber1.text = subtraction.toString()
                fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                if (Integer.parseInt(fragBinding.editTextNumber1.text.toString()) <= 0){
                    fragBinding.editTextNumber1.text = lost
                }
            }
        }

        fragBinding.buttonclear.setOnClickListener() {
            if (fragBinding.textView1.text == "") {
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "You have to enter a number first!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                var emptyString = ""
                fragBinding.textView1.text = emptyString
            }
        }

        fragBinding.buttonreset.setOnClickListener() {
            val action = DuelOnePlayerFragmentDirections.actionDuelOnePlayerFragmentToDuelFragment()
            findNavController().navigate(action)
            val action2 = DuelFragmentDirections.actionDuelFragmentToDuelOnePlayerFragment()
            findNavController().navigate(action2)
        }
        return root
    }

    private fun Chronometer(){
        val chronometer = fragBinding.chronometer
        chronometer.start()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            DuelOnePlayerFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}