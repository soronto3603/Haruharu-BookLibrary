package com.booklibrary.haruharu.booklibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.book_lib_recyclerview_item.view.*
import kotlinx.android.synthetic.main.book_search_recyclerview_item.view.*
import kotlinx.android.synthetic.main.fragment_mylib.*
import java.util.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
    }
}

class MylibFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyLibRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    var myDataset=arrayListOf<Model.BookItem>()
    var myView:View?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.fragment_mylib, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))
//        myDataset.add(Model.BookItem("123","123","123","123",1,1,"123","123",1,1))

        myView=view
        myDataset=selectBook()

        viewManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager
        init_recyclerview()


    }
    fun init_recyclerview(){
        // 뷰 어댑처 추가
        viewAdapter = MyLibRecyclerViewAdapter(myDataset)
        viewAdapter.onItemClick = {
            Log.d("MyLibFragment","ItemClicked")
        }
        viewAdapter.onItemDeleteClick = {v->
            Log.d("number",v.book_lib_id.text.toString())
            deleteBookAt(v.book_lib_id.text.toString().toInt())
        }
        viewAdapter.onHeaderClick = {
            var intent = Intent(activity,BookSearchActivity::class.java)
            startActivityForResult(intent,3)
        }
        viewAdapter.my_activity=activity
        recyclerView = myView!!.findViewById<RecyclerView>(R.id.book_lib_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
    fun selectBook():ArrayList<Model.BookItem>{
        val dbHelper=BookDB(activity)
        val db=dbHelper.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM MyBook",null)

        var db_result= arrayListOf<Model.BookItem>()

        cursor.moveToFirst()
        while(!cursor.isAfterLast()){
            var id=cursor.getInt(0)
            var title=cursor.getString(1)
            var link=cursor.getString(2)
            var image=cursor.getString(3)
            var author=cursor.getString(4)
            var price=cursor.getInt(5)
            var discount=cursor.getInt(6)
            var publisher=cursor.getString(7)
            var description=cursor.getString(8)
            var page=cursor.getInt(9)
            var readed_page=cursor.getInt(10)
            db_result.add(Model.BookItem(id,title,link,image,author,price,discount,publisher,description,page,readed_page))
            cursor.moveToNext()
        }
        cursor.close()
        return db_result
    }
    fun deleteBookAt(no:Int){
        val dbHelper=BookDB(activity)
        val db=dbHelper.readableDatabase
        Log.d("MyLibFragment","DELETE FROM MyBook WHERE id="+no)
        db.rawQuery("DELETE FROM MyBook WHERE id="+no,null)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==3){
            //데이터 추가 완료

            myDataset=selectBook()
            myDataset.reverse()
            Handler().postDelayed( { init_recyclerview() }, 250)

            Log.d("HomeFragment","data add OK")

            // need data refresh
        }else{
            Log.d("HomeFragment","data fail")
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

