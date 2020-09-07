package com.engin.covid19.quizes;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.engin.covid19.R;

public class ResultQuizActivity extends AppCompatActivity {
    TextView testSonuc,testSonucAciklama;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
        testSonuc = findViewById(R.id.testSonuc);
        testSonucAciklama = findViewById(R.id.testSonucAciklama);
        lottieAnimationView = findViewById(R.id.animation_view);
        int point=getIntent().getIntExtra("Point",0);
        if (point <2){
            lottieAnimationView.setAnimation(R.raw.covid_19_protect);
            testSonuc.setText("Sağlığınız Gayet İyi");
            testSonucAciklama.setText("Sağlığınız Ne kadar iyi gözüksede Tedbirli Olmalı");
        }
        else{
            lottieAnimationView.setAnimation(R.raw.hospital);
            testSonuc.setText("Risk Taşıyorsunuz");
            testSonucAciklama.setText("Hemen Bir Sağlık Kuruluşuna Gitmelisiniz !");
        }
    }
}