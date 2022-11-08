package ru.yakaska.mireadaimyo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import ru.yakaska.mireadaimyo.di.dataModule
import ru.yakaska.mireadaimyo.di.uiModule

class MireaDaimyoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MireaDaimyoApplication)
            modules(dataModule, uiModule)
        }
    }

}