package com.example.briefing_android.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.example.briefing_android.R
import com.example.briefing_android.api.*
import com.example.briefing_android.main.MainActivity

class SignUpPwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pw)

        val pw_back : ImageButton = findViewById(R.id.pw_back)
        val pw_confirm : TextView = findViewById(R.id.pw_confirm)
        val edit_up_pw1 : EditText = findViewById(R.id.edit_up_pw)
        val clear_text4 : ImageButton = findViewById(R.id.clear_text4)
        val edit_up_pw2 : EditText = findViewById(R.id.edit_up_pw2)
        val clear_text5 : ImageButton = findViewById(R.id.clear_text5)
        val sign_up_btn2 : Button = findViewById(R.id.sign_up_btn2)


        val colorMain = getColor(R.color.colorMain)
        val red = getColor(R.color.red)

        //1. 이전 화면 돌아가기
        pw_back.setOnClickListener {
            finish()
        }

        //2. 비밀번호 형식확인
        edit_up_pw2.addTextChangedListener(object : TextWatcher {
            //입력이 끝났을 때
            //4. 비밀번호 일치하는지 확인
            override fun afterTextChanged(p0: Editable?) {
                if(edit_up_pw1.getText().toString().equals(edit_up_pw2.getText().toString())){
                    pw_confirm.setText("비밀번호가 일치합니다.")
                    pw_confirm.setTextColor(colorMain)
                    // 가입하기 버튼 활성화
                    sign_up_btn2.isEnabled=true
                }
                else{
                    pw_confirm.setText("비밀번호가 일치하지 않습니다.")
                    pw_confirm.setTextColor(red)
                    // 가입하기 버튼 비활성화
                    sign_up_btn2.isEnabled=false
                }
            }
            //입력하기 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //텍스트 변화가 있을 시
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(edit_up_pw1.getText().toString().equals(edit_up_pw2.getText().toString())){
                    pw_confirm.setText("비밀번호가 일치합니다.")
                    pw_confirm.setTextColor(colorMain)
                    // 가입하기 버튼 활성화
                    sign_up_btn2.isEnabled=true
                }
                else{
                    pw_confirm.setText("비밀번호가 일치하지 않습니다.")
                    pw_confirm.setTextColor(red)
                    // 가입하기 버튼 비활성화
                    sign_up_btn2.isEnabled=false
                }
            }
        })

        //3. clear 버튼 이벤트
        clear_text4.setOnClickListener {
            edit_up_pw1.setText("") // 비밀번호 입력
        }
        clear_text5.setOnClickListener {
            edit_up_pw2.setText("") // 비밀번호 확인
        }


        //5. 가입하기 버튼 이벤트
        sign_up_btn2.setOnClickListener {

            // 전 액티비티에서 받아온 email값과 이 액티비티의 pw값을 서버에 보내줌
            var email = intent.getStringExtra("email2")
            var pw = edit_up_pw1.text.toString()

            val callSignup = UserServiceImpl.SignUpService.requestSignUp(signupRequest = SignupRequest(email, pw))

            callSignup.safeEnqueue(onResponse = {
                Log.v("call", callSignup.toString()+callSignup.isExecuted.toString()+callSignup.request()+it.body().toString()+it.message().toString())
                if(it.isSuccessful){
                    Log.v("성공", it.message())
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG)
                    val login = Intent(this, SignUpSuccessActivity::class.java)
                    startActivity(login)
                }else{
                    Log.v("회원가입 실패", "$$$$$$$$$$$$$$$$$$$$$$")
                }
            })


        }

    }
}