package com.haka.memoment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults

// TODO 정렬을 시간 최근 순으로 정렬하기
// TODO 메모 수정, 삭제 구현
// TODO 메모 searchView로 찾는 기능 구현
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var addMemo: FloatingActionButton
//    private lateinit var memoRecyclerview: RecyclerView
    private lateinit var memoList: ArrayList<MemoDB>
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // navigation bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // init view
        realm = Realm.getDefaultInstance()
        addMemo = findViewById(R.id.fbtnAddMemo)
//        memoRecyclerview = findViewById(R.id.memoRecycler)

//        // setOnclick

        addMemo.setOnClickListener {

            startActivity(Intent(this, AddMemoActivity::class.java))
            finish()

        }
//        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

//        getAllMemo()
    }

    //
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
////        return super.onCreateOptionsMenu(menu)
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun getAllMemo() {
//        memoList = ArrayList()
//
//        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()
//
//        memoRecyclerview.adapter = MemoAdapter(this, results)
//        memoRecyclerview.adapter!!.notifyDataSetChanged()
//
//    }
}