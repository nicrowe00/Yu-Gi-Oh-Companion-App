package ie.wit.yugiohcompanionapp.ui.dueltwoplayer

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.FragmentDueltwoplayerBinding
import ie.wit.yugiohcompanionapp.ui.duel.DuelFragmentDirections
import ie.wit.yugiohcompanionapp.main.YugiohCompanion

class DuelTwoPlayerFragment : Fragment() {

    lateinit var app: YugiohCompanion
    private var _fragBinding: FragmentDueltwoplayerBinding? = null
    private val fragBinding get() = _fragBinding!!
    private var editTextNumber1Selected: Boolean? = false
    private var editTextNumber2Selected: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as YugiohCompanion
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDueltwoplayerBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        Chronometer()
        var pointsdrop = MediaPlayer.create(activity?.applicationContext, R.raw.points_drop)
        val lost: String = "Duel Lost"
        fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
        fragBinding.progressBar2.progress = Integer.parseInt(fragBinding.editTextNumber2.text.toString())

        fragBinding.editTextNumber1.setOnClickListener(){
            fragBinding.editTextNumber1.setBackgroundColor(Color.BLUE)
            fragBinding.editTextNumber2.setBackgroundColor(Color.parseColor("#00000000"))
            editTextNumber1Selected = true
            editTextNumber2Selected = false
        }

        fragBinding.editTextNumber2.setOnClickListener(){
            fragBinding.editTextNumber2.setBackgroundColor(Color.BLUE)
            fragBinding.editTextNumber1.setBackgroundColor(Color.TRANSPARENT)
            editTextNumber2Selected = true
            editTextNumber1Selected = false
        }

        fragBinding.button.setOnClickListener(){
            var string = "1"
            fragBinding.textView3.append(string)
        }

        fragBinding.button2.setOnClickListener(){
            var string = "2"
            fragBinding.textView3.append(string)
        }

        fragBinding.button3.setOnClickListener(){
            var string = "3"
            fragBinding.textView3.append(string)
        }

        fragBinding.button4.setOnClickListener(){
            var string = "4"
            fragBinding.textView3.append(string)
        }

        fragBinding.button5.setOnClickListener(){
            var string = "5"
            fragBinding.textView3.append(string)
        }

        fragBinding.button6.setOnClickListener(){
            var string = "6"
            fragBinding.textView3.append(string)
        }

        fragBinding.button7.setOnClickListener(){
            var string = "7"
            fragBinding.textView3.append(string)
        }

        fragBinding.button8.setOnClickListener(){
            var string = "8"
            fragBinding.textView3.append(string)
        }

        fragBinding.button9.setOnClickListener(){
            var string = "9"
            fragBinding.textView3.append(string)
        }

        fragBinding.button0.setOnClickListener(){
            var string = "0"
            fragBinding.textView3.append(string)
        }

        fragBinding.button00.setOnClickListener(){
            var string = "00"
            fragBinding.textView3.append(string)
        }

        fragBinding.button000.setOnClickListener(){
            var string = "000"
            fragBinding.textView3.append(string)
        }

        fragBinding.buttonplus.setOnClickListener(){
            if (fragBinding.textView3.text == ""){
                val toast = Toast.makeText(activity?.applicationContext,"You have to enter a number first!",
                    Toast.LENGTH_SHORT)
                toast.show()
            } else if(fragBinding.editTextNumber1.text == lost){
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if(fragBinding.editTextNumber2.text == lost){
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (editTextNumber1Selected == true){
                var score = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                var points = Integer.parseInt(fragBinding.textView3.text.toString())
                pointsdrop.start()
                Thread.sleep(2800)
                var addition = score + points
                fragBinding.editTextNumber1.text = addition.toString()
                fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
            } else if (editTextNumber2Selected == true){
                var score = Integer.parseInt(fragBinding.editTextNumber2.text.toString())
                var points = Integer.parseInt(fragBinding.textView3.text.toString())
                var addition = score + points
                pointsdrop.start()
                Thread.sleep(2800)
                fragBinding.editTextNumber2.text = addition.toString()
                fragBinding.progressBar2.progress = Integer.parseInt(fragBinding.editTextNumber2.text.toString())
            }
        }

        fragBinding.buttonminus.setOnClickListener(){
            if (fragBinding.textView3.text == ""){
                val toast = Toast.makeText(activity?.applicationContext,"You have to enter a number first!",
                    Toast.LENGTH_SHORT)
                toast.show()
            } else if(fragBinding.editTextNumber1.text == lost){
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if(fragBinding.editTextNumber2.text == lost){
                val toast = Toast.makeText(
                    activity?.applicationContext, "Please hit the reset button!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (editTextNumber1Selected == true){
                var score = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                var points = Integer.parseInt(fragBinding.textView3.text.toString())
                var subtraction = score - points
                pointsdrop.start()
                Thread.sleep(2800)
                fragBinding.editTextNumber1.text = subtraction.toString()
                fragBinding.progressBar.progress = Integer.parseInt(fragBinding.editTextNumber1.text.toString())
                if (Integer.parseInt(fragBinding.editTextNumber1.text.toString()) <= 0){
                    fragBinding.editTextNumber1.text = lost
                }
            }
            else if (editTextNumber2Selected == true){
                var score = Integer.parseInt(fragBinding.editTextNumber2.text.toString())
                var points = Integer.parseInt(fragBinding.textView3.text.toString())
                var subtraction = score - points
                pointsdrop.start()
                Thread.sleep(2800)
                fragBinding.editTextNumber2.text = subtraction.toString()
                fragBinding.progressBar2.progress = Integer.parseInt(fragBinding.editTextNumber2.text.toString())
                if (Integer.parseInt(fragBinding.editTextNumber2.text.toString()) <= 0){
                    fragBinding.editTextNumber2.text = lost
                }
            }
        }

        fragBinding.buttonclear.setOnClickListener(){
            if (fragBinding.textView3.text == ""){
                val toast = Toast.makeText(activity?.applicationContext,"You have to enter a number first!",
                    Toast.LENGTH_SHORT)
                toast.show()
            } else {
                var emptyString = ""
                fragBinding.textView3.text = emptyString
            }
        }

        fragBinding.buttonreset.setOnClickListener(){
            val action = DuelTwoPlayerFragmentDirections.actionDuelTwoPlayerFragmentToDuelFragment()
            findNavController().navigate(action)
            val action2 = DuelFragmentDirections.actionDuelFragmentToDuelTwoPlayerFragment()
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
            DuelTwoPlayerFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
