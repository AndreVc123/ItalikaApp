package com.dev.bluecode.applicationitalika

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.dev.bluecode.applicationitalika.common.model.product.Product
import com.dev.bluecode.applicationitalika.common.model.realm.RealmCart
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration

class ItalikaApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initRealm()
    }

     private fun initRealm() {
        Realm.init(this)
        val realConfiguration = RealmConfiguration.Builder()
            .name("italika.realm")
            .schemaVersion(0)
            .migration(RealmMigrations()).build()
        Realm.setDefaultConfiguration(realConfiguration)
    }

    private inner class RealmMigrations : RealmMigration {
        override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
            val schema = realm.schema
        }
    }


    companion object {
        var appContext: Context? = null
        var products: MutableList<Product>? = mutableListOf()
        var cart: RealmCart? = null
    }
}