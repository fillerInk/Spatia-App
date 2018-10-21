package appmaster.sreeram.example.com.spatia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {

    private static int splashTime = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashTime);
    }
}
