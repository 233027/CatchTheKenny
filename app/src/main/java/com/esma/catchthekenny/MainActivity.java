package com.esma.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    TextView scoreText;
    int score;

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;

    ImageView[] imageArray;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize : onCreate üstünde tanımladığım değişkeni
        //onCreate altında başlatıyoruz

        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        imageView =  findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[] {imageView,imageView2,imageView3,imageView4,imageView5,
        imageView6,imageView7,imageView8,imageView9};

        hideImages();

        score = 0;

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {//her bir saniye ne olacak
                timeText.setText("Time : " + l/1000);
            }

            @Override
            public void onFinish() {//süre bitince
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);//runabble ı kapattık
                for(ImageView image : imageArray){ //zaman bitince resimler de kapansın hepsi
                    image.setVisibility(View.INVISIBLE);//resimleri sakla
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", (dialogInterface, i) -> {

                    //restart
                    //bir aktiviteyi baştan çağırmak için , onCreate i baştan başlamak için
                    //böyle bir yöntemi kullana biliriz

                    Intent intent = getIntent();
                    finish();//uygulamayı yormamak için güncel aktiviteyi tamamen bitirmemiz gerekiyor
                    startActivity(intent); //aktivitemizi yeniden başlatıyoruz

                });
                alert.setNegativeButton("No", (dialogInterface, i) -> {
                    Toast.makeText(MainActivity.this,"Game Over!" , Toast.LENGTH_LONG).show();
                });
                alert.show();
            }
        }.start();
    }

    public void increaseScore(View view){

        score++;
        scoreText.setText("Score :" +score);
    }

    public  void hideImages(){//resimleri saklama metodu

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //Runnable nin içinde tüm diziyi görünmez hale getiridik
                //sonra bir tane rastgele değer bulup değeri dizi içersinde
                //görünür hale getiricez
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);//resimleri sakla
                }
                Random random= new Random();
                int i = random.nextInt(9);//0-8 arasında rasgele değer getiricek
                imageArray[i].setVisibility(View.VISIBLE);//Görünür yap

                handler.postDelayed(this,500);

            }
        };
        handler.post(runnable);
    }
}