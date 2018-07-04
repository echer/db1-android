package br.com.db1.githubwrapper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected ApplicationComponent applicationComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencyInjection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDependencyInjection();
    }

    @Override
    protected void onPause() {
        releaseDependencyInjection();
        super.onPause();
    }

    protected void initDependencyInjection() {
        if (applicationComponent == null) {
            applicationComponent = ((BaseApplication) getApplication()).getApplicationComponent();
            applicationComponent.inject(this);
        }
    }

    private void releaseDependencyInjection() {
        applicationComponent = null;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}


