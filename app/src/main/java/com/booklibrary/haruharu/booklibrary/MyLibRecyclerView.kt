package com.booklibrary.haruharu.booklibrary

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.book_lib_recyclerview_item.view.*

class MyLibRecyclerViewAdapter(var myDataset:ArrayList<Model.BookItem>?):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var my_activity: Context?=null
    private val TYPE_HEADER = 0
    private val TYPE_ITEM =1
    private val TYPE_FOOTER = 2

    class ItemHolder(val mViewItem: ConstraintLayout) : RecyclerView.ViewHolder(mViewItem)
    class HeaderHolder(val mViewItem:ConstraintLayout) : RecyclerView.ViewHolder(mViewItem)

    var onHeaderClick: (()->Unit)? = null
    var onItemClick: (()->Unit)?=null
    var onItemDeleteClick:((v:View)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==TYPE_ITEM){
            val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.book_lib_recyclerview_item,parent,false)
            itemView.setOnClickListener{view->
                onItemClick!!.invoke()
            }
            return ItemHolder(itemView as ConstraintLayout)
        }else if(viewType==TYPE_HEADER){
            val headView= LayoutInflater.from(parent!!.context).inflate(R.layout.book_lib_recyclerview_header,parent,false)
            headView.setOnClickListener{view->
                onHeaderClick!!.invoke()
            }
            return HeaderHolder(headView as ConstraintLayout)
        }else{
            val mainView = LayoutInflater.from(parent!!.context).inflate(R.layout.book_lib_recyclerview_item,parent,false)
            return ItemHolder(mainView as ConstraintLayout)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(TYPE_HEADER==position){
            return TYPE_HEADER
        }else if(TYPE_FOOTER==position){
            return TYPE_FOOTER
        }else{
            return TYPE_ITEM
        }
    }
    override fun getItemCount(): Int {
        return myDataset!!.size+1
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemHolder){
            //데이터 없음

            if(myDataset!!.size==0){
                //사실 푸터에 넣어야할듯;
                holder.mViewItem.book_lib_title.text="데이터가 없습니다."
                holder.mViewItem.book_lib_gage.text="데이터가 없습니다."
            }else{
                holder.mViewItem.book_lib_title.text=myDataset!![position-1].title
                holder.mViewItem.book_lib_gage.text=myDataset!![position-1].readedPage.toString()

                holder.mViewItem.book_lib_id.text=myDataset!![position-1].no.toString()

                val url_string=myDataset!![position-1].image
                var img_view=holder.mViewItem.book_lib_image
                Glide.with(my_activity!!)
                        .load(url_string)
                        .into(img_view)

                holder.mViewItem.book_lib_del_btn.setOnClickListener {v->
                    onItemDeleteClick!!.invoke(v)
                }
            }
        }
    }
}

//class MyLibRecyclerViewAdapter(var myDataset: ArrayList<Model.BookItem>) :
//        RecyclerView.Adapter<MyLibRecyclerViewAdapter.ViewHolder>() {
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder.
//    // Each data item is just a string in this case that is shown in a TextView.
//
//    class ViewHolder(val mViewItem: ConstraintLayout) : RecyclerView.ViewHolder(mViewItem)
//
//
//    var onClick: (()->Unit)? = null
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): MyLibRecyclerViewAdapter.ViewHolder {
//
//        // create a new view
//        val mViewItem = LayoutInflater.from(parent.context)
//                .inflate(R.layout.book_search_recyclerview_item, parent, false) as ConstraintLayout
//        // set the view's size, margins, paddings and layout parameters
//        mViewItem.setOnClickListener { view ->
//            Log.d("SelectedBookItem",view.book_lib_title.text.toString())
//
//            //널 값 에러 막기위해 인ㅂ보크
//            onClick?.invoke()
//        }
//        return ViewHolder(mViewItem)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
////        holder.mViewItem.search_book_recyclerview_item_image_tag.text=myDataset[position].image
////        holder.mViewItem.search_book_recyclerview_item_title.text = myDataset[position].title
////        holder.mViewItem.search_book_recyclerview_item_author.text = myDataset[position].author
////        holder.mViewItem.search_book_recyclerview_item_price.text= myDataset[position].price.toString()
//        holder.mViewItem.book_lib_title.text=myDataset[position].title
//        holder.mViewItem.book_lib_gage.text=myDataset[position].readedPage.toString()
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = myDataset.size
//}