package com.example.briefing_android.summary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity
import org.w3c.dom.Text

class body_text: AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    private lateinit var tv_body :TextView
    private lateinit var tv_title :TextView
    private lateinit var tv_channelname :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.body_text)

        /*
        val script = intent.getStringExtra("script")
        val title = intent.getStringExtra("tv_title")
        val channelname = intent.getStringExtra("tv_channelname")
        */

        tv_body = findViewById(R.id.tv_body)
        tv_title = findViewById(R.id.tv_title)
        tv_channelname = findViewById(R.id.tv_channelname)

/*
tv_body.setText(script)
tv_title.setText(title)
tv_channelname.setText(channelname)
*/

        val script = "춘천닭갈비에 버섯소불고기파송송 육개장의 왕새우튀김무덤까지 자 먹방 중에 한 대사이거나 어떤 광고 문구같기도 한데 요즘 화제가 되고 있는 직장 구내식당 메뉴라고 합니다. 영상으로 한번 둘러보시죠. 점심을 먹기 위해 줄을 선 직장인들, 밥과 국 외에도 반찬만 6가진 이른바 칠첩 밥상입니다. 또 다른 식당 메뉴엔 직접 튀긴 통닭은 물론 민물장어나 스테이크까지 올라 있습니다. 최근 직장인들 사이에 월급만큼이나 중요한 근무 조건으로 구내식당이 손꼽히고 있다는데요. 온라인 커뮤니티 등에는 구내식당성지라는 목록까지 돌고 있는데 업계 최고 또는 지역최고의 식단을 자랑하는 직장들에 구내식다의 차림표와 사진 위치 등이 공유되고 있을 정도입니다. 이른바 성지명단에 오르기 위한 조건과 기준도 나름 있다는데요. 반찬가지수만 많다고 명단에 오르는 건 아니고 맛은 기본이고 반조리음식이나 가공 식품인지 여부에 재료의 신선도나 칼로리까지 꼼꼼하게 비교하고 따지는 수많은 누리꾼의 비공식 평가를 통과해야 명단에 이름을 올린다고 합니다. 이런 신세대 직장 문화에 부응하고자 일부 회사들은 아예 스타 셰프를 초청한 특씨 이벤트도 진행하기도 한다는데요. 한발 더 나아가 회사 식단으로 살도 빼고 혈당도 관히해주는 헬스케어 서비스를 제공하는 것도 있다고 하는데 코로나 이후로 회식이다 단체 식사가 어려워지면서 구내식당을 이용하는 직장인들이 자연스럽게 늘고 있다보니 생긴 또 다른 현상 중 하나인 셈인데 올해 사조원대로 훌쩍 성장한 직장 등 단체 급식 업체 시장의 경쟁도 점점 치열해지고 있다고 합니다. 과거의 회사 밥하면 맛도 영양도 부실하다 이런 인식이 강했는데요 구내식당의 화려한 변신같은 직장인으로서 저도 응원하겠습니다!"
        val title = "[재택플러스] \"연봉보다 부럽다\"?‥'구내식당'의 변신 (2021.09.24/뉴스투데이/MBC)"
        val channelname = "MBCNEWS"

        tv_body.setText(script)
        tv_title.setText(title)
        tv_channelname.setText(channelname)


        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
        finish()
        }
    }
}