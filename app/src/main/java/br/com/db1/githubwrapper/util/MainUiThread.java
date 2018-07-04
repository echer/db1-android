package br.com.db1.githubwrapper.util;

import android.os.Handler;
import android.os.Looper;

public class MainUiThread {

    private Handler handler;

    public MainUiThread() {
        handler = new Handler(Looper.getMainLooper());
    }


    public void post(Runnable runnable) {
        handler.post(runnable);
    }

}
