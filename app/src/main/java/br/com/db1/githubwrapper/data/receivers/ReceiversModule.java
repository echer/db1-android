package br.com.db1.githubwrapper.data.receivers;

import android.content.Context;
import android.content.IntentFilter;

import br.com.db1.githubwrapper.di.ActivityContext;
import br.com.db1.githubwrapper.di.ActivityScope;
import br.com.db1.githubwrapper.util.NetworkHelper;
import dagger.Module;
import dagger.Provides;
import rx.subjects.PublishSubject;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

@Module
public class ReceiversModule {

    @Provides
    @ActivityScope
    public ConnectivityBroadcastReceiver provideConnectivityBroadcastReceiver(@ActivityContext Context context, NetworkHelper networkHelper) {
        IntentFilter intentFilter = new IntentFilter(CONNECTIVITY_ACTION);
        PublishSubject<Boolean> publishSubject = PublishSubject.create();
        return new ConnectivityBroadcastReceiver(context, networkHelper, intentFilter, publishSubject);
    }
}