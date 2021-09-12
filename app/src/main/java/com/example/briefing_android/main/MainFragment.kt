package com.example.briefing_android.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.briefing_android.R
import com.example.briefing_android.sign.SignInIdActivity
import com.example.briefing_android.summary.SummaryActivity2

class MainFragment : Fragment() {
    private lateinit var edit_url: EditText
    private lateinit var btn_serach: Button
    private lateinit var clear_url: ImageButton
    private lateinit var logout: TextView
    private lateinit var url : String
    private lateinit var urlcode : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_slide_page_main, container, false)


        return view
        //return inflater.inflate(R.layout.fragment_slide_page_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_url = view.findViewById(R.id.edit_url)
        clear_url = view.findViewById(R.id.clear_url)
        btn_serach = view.findViewById(R.id.btn_serach)
        logout = view.findViewById(R.id.logout)

        //1. url 입력
        edit_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { // 입력 끝날 때 작동
                if (edit_url.getText().toString().equals("")) { // url 안적었을 시
                    btn_serach.isEnabled=false // 버튼 비활성화
                } else {  //버튼 활성화
                    btn_serach.isEnabled=true
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 하기 전 작동

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 타이핑 되는 텍스트에 변화가 있으면 작동
                if (edit_url.getText().toString().equals("")) { // url 안적었을 시
                    btn_serach.isEnabled=false // 버튼 비활성화
                } else {  //버튼 활성화
                    btn_serach.isEnabled=true
                }
            }
        })

        // 2. clear 버튼 이벤트
        clear_url.setOnClickListener {
            edit_url.setText("")
        }



        // 3. 검색 버튼 이벤트
        btn_serach.setOnClickListener {
            // summary액티비티로 이동
            url =  edit_url.getText().toString()
            urlcode = url.substring(32,url.length)
            Log.v("summary url",urlcode)
            val intent = Intent(getActivity(), SummaryActivity2::class.java)
            intent.putExtra("url",urlcode)
            startActivity(intent)
        }

        // 4. 로그아웃 이벤트
        logout.setOnClickListener {
            //val intent = Intent(this, SignInIdActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // 실행할 액티비티가 이미 스택에 존재하면 해당 액티비티 위에 존재하는 다른 액티비티 모두 종료
            startActivity(Intent(getActivity(), SignInIdActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}