package br.com.db1.githubwrapper.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.ui.github.GithubActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void openGitHubWrapperActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, GithubActivity.class);
            startActivity(intent);
            finish();
        }, 1300);
    }
}
