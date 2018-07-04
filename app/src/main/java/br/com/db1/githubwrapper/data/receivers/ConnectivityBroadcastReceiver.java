package br.com.db1.githubwrapper.data.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import br.com.db1.githubwrapper.util.NetworkHelper;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private NetworkHelper networkHelper;
    private IntentFilter connectivityIntentFilter;
    private PublishSubject<Boolean> publishSubject;


    public ConnectivityBroadcastReceiver(Context context, NetworkHelper networkHelper,
                                         IntentFilter connectivityIntentFilter, PublishSubject<Boolean> publishSubject) {
        super();
        this.context = context;
        this.networkHelper = networkHelper;
        this.connectivityIntentFilter = connectivityIntentFilter;
        this.publishSubject = publishSubject;
        init();
    }

    private void init() {
        registerReceiver();
    }

    public Observable<Boolean> registerReceiver() {
        context.registerReceiver(this, connectivityIntentFilter);
        return publishSubject;
    }

    public void unregisterReceiver() {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (networkHelper.isNetworkAvailable(context)) {
            notifyNetworkAvailable();
        } else {
            notifyNetworkUnAvailable();
        }
    }

    private void notifyNetworkAvailable() {
        publishSubject.onNext(true);
    }

    private void notifyNetworkUnAvailable() {
        publishSubject.onNext(false);
    }

}
