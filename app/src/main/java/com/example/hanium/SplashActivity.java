package com.example.hanium;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import java.lang.InterruptedException;
import android.content.Intent;

public class SplashActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(1300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
