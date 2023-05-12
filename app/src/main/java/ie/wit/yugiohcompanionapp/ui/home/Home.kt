package ie.wit.yugiohcompanionapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import ie.wit.yugiohcompanionapp.R
import ie.wit.yugiohcompanionapp.databinding.HomeBinding
import ie.wit.yugiohcompanionapp.databinding.NavHeaderBinding
import ie.wit.yugiohcompanionapp.ui.login.LoggedInViewModel
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.findNavController
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import ie.wit.yugiohcompanionapp.firebase.FirebaseImageManager
import ie.wit.yugiohcompanionapp.ui.login.Login
import ie.wit.yugiohcompanionapp.utils.customTransformation
import ie.wit.yugiohcompanionapp.utils.readImageUri
import ie.wit.yugiohcompanionapp.utils.showImagePicker
import timber.log.Timber




class Home : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var homeBinding : HomeBinding
    private lateinit var navHeaderBinding : NavHeaderBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loggedInViewModel : LoggedInViewModel
    private lateinit var headerView : View
    private lateinit var intentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    homeBinding = HomeBinding.inflate(layoutInflater)
    setContentView(homeBinding.root)
    drawerLayout = homeBinding.drawerLayout
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    toolbar.setTitleTextColor(resources.getColor(R.color.white))
    setSupportActionBar(toolbar)
    initNavHeader()
    val navController = findNavController(R.id.nav_host_fragment)
    appBarConfiguration = AppBarConfiguration(setOf(
        R.id.duelFragment, R.id.playersFragment), drawerLayout)
    setupActionBarWithNavController(navController, appBarConfiguration)
    val navView = homeBinding.navView
    navView.setupWithNavController(navController)
}

public override fun onStart() {
    super.onStart()
    loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
    loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
        if (firebaseUser != null)
            updateNavHeader(firebaseUser)
    })

    loggedInViewModel.loggedOut.observe(this, Observer { loggedout ->
        if (loggedout) {
            startActivity(Intent(this, Login::class.java))
        }
    })
    registerImagePickerCallback()
}

    private fun updateNavHeader(currentUser: FirebaseUser) {
        FirebaseImageManager.imageUri.observe(this) { result ->
            if (result == Uri.EMPTY) {
                Timber.i("DX NO Existing imageUri")
                if (currentUser.photoUrl != null) {
                    FirebaseImageManager.updateUserImage(
                        currentUser.uid,
                        currentUser.photoUrl,
                        navHeaderBinding.navHeaderImage,
                        false
                    )
                } else {
                    Timber.i("DX Loading Existing Default imageUri")
                    FirebaseImageManager.updateDefaultImage(
                        currentUser.uid,
                        R.drawable.yugi,
                        navHeaderBinding.navHeaderImage
                    )
                }        } else
            {
                Timber.i("DX Loading Existing imageUri")
                FirebaseImageManager.updateUserImage(
                    currentUser.uid,
                    FirebaseImageManager.imageUri.value,
                    navHeaderBinding.navHeaderImage, false
                )
            }    }
        navHeaderBinding.navHeaderEmail.text = currentUser.email
        if(currentUser.displayName != null)
            navHeaderBinding.navHeaderName.text = currentUser.displayName
    }



    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}

    fun signOut(item: MenuItem){
        loggedInViewModel.logOut()
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun initNavHeader() {
        Timber.i("DX Init Nav Header")
        headerView = homeBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderBinding.bind(headerView)
        navHeaderBinding.navHeaderImage.setOnClickListener {
            showImagePicker(intentLauncher)
        }
    }

    private fun registerImagePickerCallback() {
        intentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            FirebaseImageManager
                                .updateUserImage(loggedInViewModel.liveFirebaseUser.value!!.uid,
                                    readImageUri(result.resultCode, result.data),
                                    navHeaderBinding.navHeaderImage,
                                    true)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}