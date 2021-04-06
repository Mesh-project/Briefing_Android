package com.example.briefing_android.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity

class SignInPwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_pw)

        val login_back : ImageButton = findViewById(R.id.login_back)
        val edit_in_pw : EditText = findViewById(R.id.edit_in_pw)
        val check_pw : CheckBox = findViewById(R.id.check_pw)
        val sign_in_btn2 : Button = findViewById(R.id.sign_in_btn2)
        val clear_text : ImageButton = findViewById(R.id.clear_text2)

        //1. 이전 화면 돌아가기
        login_back.setOnClickListener {
            finish()
        }

        //2. 비밀번호 입력 이벤트
        // 텍스트 입력 시 버튼 활성화
        edit_in_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { // 입력 끝날 때 작동
                if (edit_in_pw.getText().toString().equals("")) { // 비밀번호 안적었을 시
                    sign_in_btn2.isEnabled=false // 버튼 비활성화
                } else {  //버튼 활성화
                    sign_in_btn2.isEnabled=true
                    //edit_in_pw.setBackgroundResource(R.drawable.btn_style)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 하기 전 작동

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
                if (edit_in_pw.getText().toString().equals("")) {
                    sign_in_btn2.isEnabled=false

                } else {
                    sign_in_btn2.isEnabled=true
                }
            }
        })

        // clear 버튼 이벤트
        clear_text.setOnClickListener {
            edit_in_pw.setText("")
        }


        //3. 비밀번호 체크박스 이벤트
        check_pw.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked){ // 체크 시 비밀번호 숨김처리 사라짐
                edit_in_pw.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                //edit_in_pw.setInputType(91)
                //edit_in_pw.inputType=91
            }else{ // 체크 풀 시 비밀번호 숨김처리
                edit_in_pw.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                //edit_in_pw.inputType=81
            }
        }

        // 5. 로그인 버튼 이벤트
        sign_in_btn2.setOnClickListener {
            // 5-1. 비밀번호 틀릴 시 다이얼로그 출력

            // 5-2. 비밀번호 맞을 시 메인화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}