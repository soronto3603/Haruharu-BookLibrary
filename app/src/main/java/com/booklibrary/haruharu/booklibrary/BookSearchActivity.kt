package com.booklibrary.haruharu.booklibrary

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.book_search_recyclerview_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class BookSearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: mRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    var myDataset=arrayListOf<Model.BookItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book_search)



        viewManager = LinearLayoutManager(this)
        // 뷰 어댑처 추가
        viewAdapter = mRecyclerViewAdapter(myDataset)
        viewAdapter.onClick = {
            var intent=Intent(this,BookAddActivity::class.java)
            startActivityForResult(intent,1)

        }


        recyclerView = findViewById<RecyclerView>(R.id.book_search_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==1){
            Log.d("ResponseCode","1")
            //MyLib Fragment 데이터 리셋
            setResult(3)
            finish()
        }else{
            Log.d("ResponseCode","99")
        }
    }
    fun searchBookToNaver(v:View){
        val rtf = Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = rtf.create(NaverAPI::class.java)

        val repos = service.searchBook("c언어",30,1)

        repos.enqueue(object :Callback<Model.NaverResponse> {
            override fun onFailure(call: Call<Model.NaverResponse>?, t: Throwable?) {
                Log.wtf("Retrofit Fail", t)

            }

            override fun onResponse(call: Call<Model.NaverResponse>?, response: Response<Model.NaverResponse>) {
                if (response.isSuccessful) {
                    // 200

                    Log.d("log", response.toString() + "," + response!!.body())
                    val bookitems=response.body()!!.items
                    for( bookitem in bookitems){
                        addData(bookitem)
                    }
                } else {
                    Log.wtf("BookAPIError",response.toString()+response!!.body())
                }
            }

        })
    }

//    add & clear test function
    fun addDataTest(v:View){
    }
    fun addData(item:Model.BookItem){
        myDataset.add(item)
        viewAdapter.notifyDataSetChanged()
    }
    fun clearDataTest(v:View){
        myDataset.clear()
        viewAdapter.notifyDataSetChanged()
    }
}
interface NaverAPI {
    @Headers(
        "X-Naver-Client-Id:O8kojWyDFVFZGHNDOkX3",
        "X-Naver-Client-Secret:pgQYhEmsvb"
    )
    @GET("v1/search/book.json")
    fun searchBook(@Query("query") query:String,
                   @Query("display") display:Int,
                   @Query("start") start:Int): Call<Model.NaverResponse>
}

class mRecyclerViewAdapter(var myDataset: ArrayList<Model.BookItem>) :
        RecyclerView.Adapter<mRecyclerViewAdapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val mViewItem: ConstraintLayout) : RecyclerView.ViewHolder(mViewItem)


    var onClick: (()->Unit)? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): mRecyclerViewAdapter.ViewHolder {

        // create a new view
        val mViewItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_search_recyclerview_item, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters
        mViewItem.setOnClickListener { view ->
            Log.d("SelectedBookItem",view.search_book_recyclerview_item_title.text.toString())

            var book_item=Model.BookItem(-1,view.search_book_recyclerview_item_title.text.toString()
                    ,"",view.search_book_recyclerview_item_image_tag.text.toString(),view.search_book_recyclerview_item_author.text.toString(),
                    view.search_book_recyclerview_item_price.text.toString().toInt(),-1,"","",0,0)

            var current_book_item=DataManager.instance
            current_book_item.selected_book_item=book_item

            //널 값 에러 막기위해 인ㅂ보크
            onClick?.invoke()
        }

        return ViewHolder(mViewItem)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mViewItem.search_book_recyclerview_item_image_tag.text=myDataset[position].image
        holder.mViewItem.search_book_recyclerview_item_title.text = myDataset[position].title
        holder.mViewItem.search_book_recyclerview_item_author.text = myDataset[position].author
        holder.mViewItem.search_book_recyclerview_item_price.text= myDataset[position].price.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
