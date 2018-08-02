package com.booklibrary.haruharu.booklibrary

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.book_search_recyclerview_item.view.*
import kotlinx.android.synthetic.main.fragment_mylib.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
    }
}

class MylibFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: mRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    var myDataset=arrayListOf<Model.BookItem>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mylib, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))

        viewManager = LinearLayoutManager(activity)
        // 뷰 어댑처 추가
        viewAdapter = mRecyclerViewAdapter(myDataset)
        viewAdapter.onClick = {


        }

        recyclerView = view.findViewById<RecyclerView>(R.id.book_lib_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        book_search_btn.setOnClickListener {
            var intent = Intent(activity, BookSearchActivity::class.java)
            startActivity(intent)
        }
    }


}

class RecomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommentation, container, false)
    }
}

class MyInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_myinfo, container, false)
    }
}

