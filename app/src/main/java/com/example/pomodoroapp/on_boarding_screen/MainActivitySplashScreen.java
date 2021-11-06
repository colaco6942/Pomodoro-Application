package com.example.pomodoroapp.on_boarding_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pomodoroapp.R;

public class MainActivitySplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    SharedPreferences OnBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image  = findViewById(R.id.imageView);
        logo   = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                OnBoardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
//                boolean isFirstTime = OnBoardingScreen.getBoolean("firstTime",true);
//                if(isFirstTime) {
//
//                    SharedPreferences.Editor editor = OnBoardingScreen.edit();
//                    editor.putBoolean("firstTime",false);
//                    editor.commit();
                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                startActivity(intent);
                finish();
//                }
//                else{
//                    Intent intent = new Intent(getApplicationContext(),HomePage.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        },SPLASH_SCREEN);
    }
}
