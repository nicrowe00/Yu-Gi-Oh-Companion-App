package ie.wit.yugiohcompanionapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.LauncherBinding

class Launcher : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var launcherBinding: LauncherBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcherBinding = LauncherBinding.inflate(layoutInflater)
        setContentView(launcherBinding.root)
        drawerLayout = launcherBinding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        val navView = launcherBinding.navView
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}