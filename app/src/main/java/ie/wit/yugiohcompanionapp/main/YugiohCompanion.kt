package ie.wit.yugiohcompanionapp.main

import android.app.Application
import ie.wit.yugiohcompanionapp.models.PlayerStore
import timber.log.Timber

class YugiohCompanion : Application() {

    lateinit var players: PlayerStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("YugiohCompanionApp Launched")
    }
}