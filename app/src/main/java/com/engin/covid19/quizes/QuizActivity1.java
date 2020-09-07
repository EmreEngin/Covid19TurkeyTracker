package com.engin.covid19.quizes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.engin.covid19.R;

import org.w3c.dom.Text;

public class QuizActivity1 extends AppCompatActivity {

    private TextView soru,ilerleme;
    private Button evetButton,hayirButton;
    private ImageView progressbar;
    private int count = 0;
    private  int point = 0;
    private String soru2;
    private String soru3;
    private String soru4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        soru = findViewById(R.id.textquesiton);
        ilerleme = findViewById(R.id.progressTv);
        evetButton= findViewById(R.id.evetButton);
        hayirButton = findViewById(R.id.hayirButton);
        progressbar = findViewById(R.id.progressbarQuestions);

        soru2 = "Son 4 gündür Kuru Öksürüğünüz Var mı ? ";
        soru3 = "Son 4 gündür Nefes Almakta Zorlanıyor musun?";
        soru4 = "Son 4 gündür Kendini halsiz hissediyor musun? ";
        evetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                point++;
                if (count == 1){
                    soru.setText(soru2);
                    ilerleme.setText("2/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar2));
                }
                else if(count == 2){
                    soru.setText(soru3);
                    ilerleme.setText("3/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar3));
                }
                else if(count == 3){
                    soru.setText(soru4);
                    ilerleme.setText("4/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar4));
                }
                else{
                    Intent intent = new Intent(QuizActivity1.this,ResultQuizActivity.class);
                    intent.putExtra("Point",point);
                    startActivity(intent);
                    finish();
                }

            }
        });
        hayirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                point--;
                if (count == 1){
                    soru.setText(soru2);
                    ilerleme.setText("2/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar2));
                }
                else if(count == 2){
                    soru.setText(soru3);
                    ilerleme.setText("3/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar3));
                }
                else if(count == 3){
                    soru.setText(soru4);
                    ilerleme.setText("4/4");
                    progressbar.setImageDrawable(getResources().getDrawable(R.drawable.progressbar4));
                }
                else{
                    Intent intent = new Intent(QuizActivity1.this,ResultQuizActivity.class);
                    intent.putExtra("Point",point);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}