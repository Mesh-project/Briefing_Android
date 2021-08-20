package com.example.briefing_android.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.briefing_android.R
import com.example.briefing_android.api.*

class SignUpIdActivity : AppCompatActivity() {

    var check: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_id)

        val edit_up_email : EditText = findViewById(R.id.edit_up_email)
        val id_back : ImageButton = findViewById(R.id.id_back)
        val sign_up_btn : Button = findViewById(R.id.sign_up_btn1)
        val clear_text : ImageButton = findViewById(R.id.clear_text3)

        val colorMain = getColor(R.color.colorMain)
        val gray2 = getColor(R.color.gray2)

        //1. 이전 화면 돌아가기
        id_back.setOnClickListener {
            finish()
        }

        // 2. 이메일 입력 이벤트
        // 2-1. 이메일 형식이 맞는지 확인?
        // 2-2. 계속하기 버튼 활성화
        edit_up_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { // 입력 끝날 때 작동
                if (edit_up_email.getText().toString().equals("")) { // 이메일 안적었을 시
                    sign_up_btn.isEnabled=false // 버튼 비활성화
                    sign_up_btn.setTextColor(gray2)
                } else {  //버튼 활성화
                    sign_up_btn.isEnabled=true
                    sign_up_btn.setTextColor(colorMain)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 하기 전 작동

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
                if (edit_up_email.getText().toString().equals("")) {
                    sign_up_btn.isEnabled=false
                    sign_up_btn.setTextColor(gray2)
                } else {
                    sign_up_btn.isEnabled=true
                    sign_up_btn.setTextColor(colorMain)
                }
            }
        })

        //3. clear 버튼 이벤트
        clear_text.setOnClickListener {
            edit_up_email.setText("")
        }

        // 4. 계속하기 버튼 이벤트
        sign_up_btn.setOnClickListener {
            // 3-1. 서버에 이메일 중복확인 요청
            /*
            val callIdCheck = UserServiceImpl.IdCheckService.SignUpIdCheck(edit_up_email.getText().toString())
            callIdCheck.safeEnqueue {
                if (it.isSuccessful) {
                    if (it.body()!!.status == 200) {
                        check = 1
                        Toast.makeText(
                            this,
                            "사용 가능한 이메일입니다.",
                            android.widget.Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(
                            this,
                            "이미 등록된 이메일입니다. 다시 입력해주세요.",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                        check = 0
                    }
                }
            }
            */
            // 3-1. 서버에 회원가입 요청 -> 비밀번호까지입력하고나서 해야하므로
            // 다음액티비티에 email 액티비티 값 저장해서 전달.
            val email2 = edit_up_email.text.toString()

            // 3-2. 성공 시 회원가입 - 비밀번호 입력 창으로 넘어감
            val intent = Intent(this, SignUpPwActivity::class.java)
            intent.putExtra("email2",email2)
            startActivity(intent)
        }

    }
}