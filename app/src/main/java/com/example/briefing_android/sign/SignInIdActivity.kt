package com.example.briefing_android.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.briefing_android.R

class SignInIdActivity : AppCompatActivity() {
   // private val sign_up_btn : Button = findViewById(R.id.sign_up_btn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_id)

        val edit_in_email : EditText = findViewById(R.id.edit_in_email)
        val goto_signup : TextView = findViewById(R.id.goto_signup)
        val sign_in_btn : Button = findViewById(R.id.sign_in_btn1)
        val clear_text : ImageButton = findViewById(R.id.clear_text)

        val colorMain = getColor(R.color.colorMain)
        val gray2 = getColor(R.color.gray2)

        // 1. 이메일 입력 이벤트
        // 1-1. 이메일 형식이 맞는지 확인?
        // 1-2. 계속하기 버튼 활성화
        edit_in_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { // 입력 끝날 때 작동
                if (edit_in_email.getText().toString().equals("")) { // 이메일 안적었을 시
                    sign_in_btn.isEnabled=false // 버튼 비활성화
                    sign_in_btn.setTextColor(gray2)
                } else {  //버튼 활성화
                    sign_in_btn.isEnabled=true
                    sign_in_btn.setTextColor(colorMain)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 하기 전 작동

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
                if (edit_in_email.getText().toString().equals("")) {
                    sign_in_btn.isEnabled=false
                    sign_in_btn.setTextColor(gray2)
                } else {
                    sign_in_btn.isEnabled=true
                    sign_in_btn.setTextColor(colorMain)
                }
            }
        })

        // clear 버튼 이벤트
        clear_text.setOnClickListener {
            edit_in_email.setText("")
        }


        // 2. 가입하기 텍스트 이벤트
        // 회원가입- 이메일 입력창으로 넘어감
        goto_signup.setOnClickListener {
            val intent = Intent(this, SignUpIdActivity::class.java)
            startActivity(intent)
        }

        // 3. 계속하기 버튼 이벤트
        sign_in_btn.setOnClickListener {
            // 3-1. 서버에 로그인 요청

            // 3-2. 성공 시 로그인 - 비밀번호 입력 창으로 넘어감
            val intent = Intent(this, SignInPwActivity::class.java)
            startActivity(intent)
        }

    }

}