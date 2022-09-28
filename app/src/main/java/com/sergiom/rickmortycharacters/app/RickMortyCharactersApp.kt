package com.sergiom.rickmortycharacters.app

import android.app.Application
import android.content.Context
import com.sergiom.rickmortycharacters.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RickMortyCharactersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
    }

    companion object {
        lateinit var instance: RickMortyCharactersApp

        fun getAppContext(): Context = instance.applicationContext
    }
}