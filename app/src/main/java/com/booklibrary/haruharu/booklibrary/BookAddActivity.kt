package com.booklibrary.haruharu.booklibrary

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class BookAddActivity : AppCompatActivity() {

    val dbHelper=BookDB(this@BookAddActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        var current_book_item=DataManager.instance
        var current_book=current_book_item.selected_book_item

        var title_view=findViewById<EditText>(R.id.add_book_txt_title)
        title_view.setText(current_book.title)

        var author_view=findViewById<TextView>(R.id.add_book_txt_author)
        author_view.text=current_book.author

        var img_view=findViewById<ImageView>(R.id.add_book_img)
        var img_source_tag=findViewById<TextView>(R.id.add_book_img_tag)
        var url_string=current_book.image
        img_source_tag.text=url_string

        Glide.with(this)
                .load(url_string)
                .into(img_view)
    }

    fun insertBook(v: View){
        var current_book_item = DataManager.instance
        var cb = current_book_item.selected_book_item

        val db=dbHelper.writableDatabase
        val values= ContentValues().apply{
            put("title",cb.title)
            put("link",cb.link)
            put("image",cb.image)
            put("author",cb.author)
            put("price",cb.price)
            put("discount",cb.discount)
            put("publisher",cb.publisher)
            put("description",cb.description)
            put("numOfPage",0)
            put("readedPage",0)
        }
        Log.d("INSERT_BOOK","INSERT BOOK FINISIH : " + cb.title + cb.link+cb.image+cb.author+cb.price+cb.discount+cb.publisher+cb.description)

        db.insert("MyBook",null, values)

        setResult(1)
        finish()
    }

    fun selectBook(v:View){
        val db=dbHelper.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM MyBook",null)

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
            Log.d("DB_SELECT","s"+id+title+link+image+author+price+discount+publisher+description)
            cursor.moveToNext()
        }
        cursor.close()
    }
}
