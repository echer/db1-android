package br.com.db1.githubwrapper.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.db1.githubwrapper.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent = new Intent(SplashActivity.this, GithubWrapperActivity.class);
                startActivity(intent);*/

                finish();
            }
        }, 1300);
    }
}
