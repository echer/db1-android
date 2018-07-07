package br.com.db1.githubwrapper;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        initApplicationComponent();
    }

    public abstract ApplicationComponent getApplicationComponent();

    public abstract void initApplicationComponent();
}
