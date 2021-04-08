package com.example.briefing_android.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity

class SignUpSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_success)

        // 로그인 하러가기
        val goto_login : TextView = findViewById(R.id.goto_login)
        goto_login.setOnClickListener {
            //val intent = Intent(this, SignInIdActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // 실행할 액티비티가 이미 스택에 존재하면 해당 액티비티 위에 존재하는 다른 액티비티 모두 종료
            startActivity(Intent(this, SignInIdActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}