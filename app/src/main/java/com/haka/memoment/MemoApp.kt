package com.haka.memoment

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MemoApp: Application() {
    override fun onCreate() {
        super.onCreate()

        //init realm

        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("memos.db")
            .deleteRealmIfMigrationNeeded()
                // 필요하면 제거
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .schemaVersion(0)
            .build()

        Realm.setDefaultConfiguration(configuration)

    }
}