package com.example.briefing_android.sign

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.briefing_android.R
import com.example.briefing_android.api.*

import com.example.briefing_android.main.MainActivity


class SignInPwActivity : AppCompatActivity() {

    private lateinit var loginData: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_pw)

        val login_back: ImageButton = findViewById(R.id.login_back)
        val edit_in_pw: EditText = findViewById(R.id.edit_in_pw)
        val check_pw: CheckBox = findViewById(R.id.check_pw)
        val sign_in_btn2: Button = findViewById(R.id.sign_in_btn2)
        val clear_text: ImageButton = findViewById(R.id.clear_text2)


        //1. 이전 화면 돌아가기
        login_back.setOnClickListener {
            finish()
        }

        //2. 비밀번호 입력 이벤트
        // 텍스트 입력 시 버튼 활성화
        edit_in_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { // 입력 끝날 때 작동
                if (edit_in_pw.getText().toString().equals("")) { // 비밀번호 안적었을 시
                    sign_in_btn2.isEnabled = false // 버튼 비활성화
                } else {  //버튼 활성화
                    sign_in_btn2.isEnabled = true
                    //edit_in_pw.setBackgroundResource(R.drawable.btn_style)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 하기 전 작동

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
                if (edit_in_pw.getText().toString().equals("")) {
                    sign_in_btn2.isEnabled = false

                } else {
                    sign_in_btn2.isEnabled = true
                }
            }
        })

        // clear 버튼 이벤트
        clear_text.setOnClickListener {
            edit_in_pw.setText("")
        }


        //3. 비밀번호 체크박스 이벤트
        check_pw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // 체크 시 비밀번호 숨김처리 사라짐
                edit_in_pw.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                //edit_in_pw.setInputType(91)
                //edit_in_pw.inputType=91
            } else { // 체크 풀 시 비밀번호 숨김처리
                edit_in_pw.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                //edit_in_pw.inputType=81
            }
        }

        // 5. 로그인 버튼 이벤트
        sign_in_btn2.setOnClickListener {
            // 전 액티비티에서 받아온 email값과 이 액티비티의 pw값을 서버에 보내줌
            var email = intent.getStringExtra("email")
            var pw = edit_in_pw.text.toString()

            val callLogin = UserServiceImpl.SignInService.requestSignIn(signinRequest = SigninRequest(email, pw))

            callLogin.safeEnqueue(onResponse =
            {
                Log.v("call", callLogin.toString()+callLogin.isExecuted.toString()+callLogin.request()+it.body().toString()+it.message().toString())
                if (it.isSuccessful) { // 5-1. 로그인 성공 시
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG)
                    loginData=it.body()!!.toString()
                    SharedPreferenceController.setUserToken(this, loginData)
                    Log.v("로그인성공!!!!!!!", loginData)

                    MySharedPreferences.setUserIdx(this, it.body()!!.user_idx.toString())
                    // 메인화면으로 이동
                    val login = Intent(this, MainActivity::class.java)
                    Log.v("signinpwactivity : user_idx전달!!!!!!!", ""+it.body()!!.user_idx)

                    startActivity(login)
                } else {
                    Log.v("로그인 실패", "$$$$$$$$$$$$$$$$$")
                    Toast.makeText(this, "이메일 또는 비밀번호가 틀립니다.", Toast.LENGTH_LONG)
                }
            })

        }
    }
}

