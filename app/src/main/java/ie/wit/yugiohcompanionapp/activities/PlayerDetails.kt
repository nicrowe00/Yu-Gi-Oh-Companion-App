package ie.wit.yugiohcompanionapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.ActivityPlayerdetailsBinding
import ie.wit.yugiohcompanionapp.main.YugiohCompanion
import ie.wit.yugiohcompanionapp.models.PlayerModel

class PlayerDetails : AppCompatActivity(){

    private lateinit var playerDetailsLayout: ActivityPlayerdetailsBinding
    lateinit var app: YugiohCompanion
    var edit = false
    var player = PlayerModel()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        playerDetailsLayout = ActivityPlayerdetailsBinding.inflate(layoutInflater)
        setContentView(playerDetailsLayout.root)
        app = application as YugiohCompanion
        title = getString(R.string.action_player_details_menu)


        if (intent.hasExtra("player_edit")){
            edit = true
            player = intent.extras?.getParcelable("player_edit")!!
            playerDetailsLayout.playername.setText(player.playername)
            playerDetailsLayout.playerwins.setText(player.playerwins)
            playerDetailsLayout.playermatches.setText(player.playermatches)
            playerDetailsLayout.playerduellinks.setText(player.playerduellinks)
            playerDetailsLayout.playermasterduel.setText(player.playermasterduel)
        }

        playerDetailsLayout.save.setOnClickListener(){
            player.playername = playerDetailsLayout.playername.text.toString()
            player.playerwins = playerDetailsLayout.playerwins.text.toString()
            player.playermatches = playerDetailsLayout.playermatches.text.toString()
            player.playerduellinks = playerDetailsLayout.playerduellinks.text.toString()
            player.playermasterduel = playerDetailsLayout.playermasterduel.text.toString()
            if (player.playername.isEmpty()){
                Toast.makeText(this, "You must enter a player name!", Toast.LENGTH_SHORT).show()
            } else {
                if (edit) {
                    app.players.update(player.copy())
                } else {
                    app.players.create(player.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_player_details, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel -> {
                finish()
            }
            R.id.delete -> {
                setResult(99)
                app.players.delete(player)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}