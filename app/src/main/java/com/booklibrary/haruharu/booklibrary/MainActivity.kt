package com.booklibrary.haruharu.booklibrary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

public class DataManager private constructor() {
    init {
//        println("This ($this) is a singleton")
    }

    private object Holder { val INSTANCE = DataManager() }

    companion object {
        val instance: DataManager by lazy { Holder.INSTANCE }
    }
    var selected_book_item=Model.BookItem(-1,"","","","",-1,-1,"","")
}

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // 뷰페이저 이니셜
    val vp: CustomViewPager by lazy { findViewById<CustomViewPager>(R.id.mViewPager) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val btn_home  by lazy{findViewById<TextView>(R.id.btn_home)}
        val btn_mylib by lazy{findViewById<TextView>(R.id.btn_mylib)}
        val btn_recommendation by lazy{findViewById<TextView>(R.id.btn_recommendation)}
        val btn_myinfo by lazy{findViewById<TextView>(R.id.btn_my_info)}

        // 뷰페이저 어댑터 연결 후 첫번째 화면 설정
        vp.adapter = ViewPagerAdapter(supportFragmentManager)
        vp.setCurrentItem(0)

        // 각 버튼에 뷰페이너 동작 리스너 달기
        btn_home.setOnClickListener(movePageListener)
        btn_mylib.setOnClickListener(movePageListener)
        btn_recommendation.setOnClickListener(movePageListener)
        btn_myinfo.setOnClickListener(movePageListener)

    }

    // 각 버튼에 리스너 뷰페이저 움지깅ㅁ
    var movePageListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_home -> {
                vp.setCurrentItem(0)
            }
            R.id.btn_mylib -> {
                vp.setCurrentItem(1)
            }
            R.id.btn_recommendation -> {
                vp.setCurrentItem(2)
            }
            R.id.btn_my_info -> {
                vp.setCurrentItem(3)
            }
        }
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun startLoginActivity(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {
//              로그인 액티비티 시작
                startLoginActivity()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    // 뷰 페이저 어댑터
    class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return HomeFragment()
                1 -> return MylibFragment()
                2 -> return RecomFragment()
                3 -> return MyInfoFragment()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }

}
