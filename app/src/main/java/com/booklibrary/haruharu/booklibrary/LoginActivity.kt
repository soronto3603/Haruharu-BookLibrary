package com.booklibrary.haruharu.booklibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun login_loginform(view: View) {
        val rtf = Retrofit.Builder()
                .baseUrl("https://haruharu.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = rtf.create(HaruharuAPI::class.java)

        val email = login_input_email.text.toString()
        val pw = login_input_pw.text.toString()

        val repos = service.loginUser(Model.User(email = email, password = pw))

        repos.enqueue(object : Callback<Model.Result> {
            override fun onFailure(call: Call<Model.Result>?, t: Throwable?) {
                Log.wtf("Retrofit Fail", t)

            }

            override fun onResponse(call: Call<Model.Result>?, response: Response<Model.Result>) {
                if (response.isSuccessful) {
                    // 200
                    Log.d("log", response.toString() + "," + response!!.body())
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // 400
                    //에러 파싱 못했음;ㅠㅠ
                    Toast.makeText(this@LoginActivity, "아이디와 패스워드를 확인하세요", Toast.LENGTH_SHORT).show()
//                    val errorString=response!!.errorBody().toString()
//                    Log.d("log",response.toString() + ","+response!!.errorBody())
                }
            }

        })
    }

    fun signup_loginform(view: View) {

        val rtf = Retrofit.Builder()
                .baseUrl("https://haruharu.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = rtf.create(HaruharuAPI::class.java)


        val email = login_input_email.text.toString()
        val pw = login_input_pw.text.toString()

//        Log.d("Signup Function]",email+","+pw)
        val repos = service.signupUser(Model.User(email = email, password = pw))

        repos.enqueue(object : Callback<Model.Result> {
            override fun onFailure(call: Call<Model.Result>?, t: Throwable?) {
                Log.wtf("Retrofit Fail", t)

            }

            override fun onResponse(call: Call<Model.Result>?, response: Response<Model.Result>) {
                if (response.isSuccessful) {
                    // 200
                    Log.d("log", response.toString() + "," + response!!.body())
                    Toast.makeText(this@LoginActivity, "성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 400
                    //에러 파싱 못했음;ㅠㅠ
                    Toast.makeText(this@LoginActivity, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show()
//                    val errorString=response!!.errorBody().toString()
//                    Log.d("log",response.toString() + ","+response!!.errorBody())
                }
            }

        })
    }

    fun change_login_form(view: View) {
        val btn_signup = findViewById<Button>(R.id.login_btn_signup)
        val text_signup = findViewById<TextView>(R.id.login_btn_signup_detail)
        btn_signup.visibility = View.GONE
        text_signup.visibility = View.GONE

        val btn_login = findViewById<Button>(R.id.login_btn_login)
        val text_login = findViewById<TextView>(R.id.login_btn_login_detail)

        text_login.visibility = View.VISIBLE
        btn_login.visibility = View.VISIBLE
    }

    fun change_signup_form(view: View) {
        val btn_signup = findViewById<Button>(R.id.login_btn_signup)
        val text_signup = findViewById<TextView>(R.id.login_btn_signup_detail)
        btn_signup.visibility = View.VISIBLE
        text_signup.visibility = View.VISIBLE

        val btn_login = findViewById<Button>(R.id.login_btn_login)
        val text_login = findViewById<TextView>(R.id.login_btn_login_detail)

        text_login.visibility = View.GONE
        btn_login.visibility = View.GONE
    }

}



interface HaruharuAPI {
    @POST("v1/api/signup")
    fun signupUser(@Body user: Model.User): Call<Model.Result>

    @POST("v1/api/login")
    fun loginUser(@Body user: Model.User): Call<Model.Result>
}