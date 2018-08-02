package com.booklibrary.haruharu.booklibrary

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.book_lib_recyclerview_item.view.*

class MyLibRecyclerViewAdapter(var myDataset: ArrayList<Model.BookItem>) :
        RecyclerView.Adapter<MyLibRecyclerViewAdapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val mViewItem: ConstraintLayout) : RecyclerView.ViewHolder(mViewItem)


    var onClick: (()->Unit)? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyLibRecyclerViewAdapter.ViewHolder {

        // create a new view
        val mViewItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_search_recyclerview_item, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters
        mViewItem.setOnClickListener { view ->
            Log.d("SelectedBookItem",view.book_lib_title.text.toString())

            //널 값 에러 막기위해 인ㅂ보크
            onClick?.invoke()
        }

        return ViewHolder(mViewItem)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mViewItem.search_book_recyclerview_item_image_tag.text=myDataset[position].image
//        holder.mViewItem.search_book_recyclerview_item_title.text = myDataset[position].title
//        holder.mViewItem.search_book_recyclerview_item_author.text = myDataset[position].author
//        holder.mViewItem.search_book_recyclerview_item_price.text= myDataset[position].price.toString()
        holder.mViewItem.book_lib_title.text=myDataset[position].title
        holder.mViewItem.book_lib_gage.text=myDataset[position].readedPage.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}