package com.booklibrary.haruharu.booklibrary

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
    }
}

class MylibFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        abstract class OnBookAddListener{
            fun onBookAddListener(bookitem:Model.BookItem) {}
        }

        val view=inflater.inflate(R.layout.fragment_mylib, container, false) as ConstraintLayout
        val book_search_btn=view.findViewById<TextView>(R.id.book_search_btn)



        book_search_btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View) {
                var intent = Intent(activity, BookSearchActivity::class.java)
                startActivity(intent)
            }
        })

        return view
    }
}

class RecomFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommentation, container, false) as ConstraintLayout
    }
}

class MyInfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_myinfo, container, false) as ConstraintLayout
    }
}

