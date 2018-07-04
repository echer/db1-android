package br.com.db1.githubwrapper;

import android.app.Application;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
    }

    public abstract ApplicationComponent getApplicationComponent();

    public abstract void initApplicationComponent();
}
