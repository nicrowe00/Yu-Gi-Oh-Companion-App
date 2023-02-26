package ie.wit.yugiohcompanionapp.main

import android.app.Application
import ie.wit.yugiohcompanionapp.models.PlayerJSONStore
import ie.wit.yugiohcompanionapp.models.PlayerStore
import timber.log.Timber
import timber.log.Timber.i

class YugiohCompanion : Application() {

    lateinit var players: PlayerStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        players = PlayerJSONStore(applicationContext)
        i("YugiohCompanionApp Launched")
    }
}