package com.tale.frppractice;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by TALE on 10/15/2014.
 */
public class BasicActivity extends ActionBarActivity {
    @InjectView(R.id.tvResult)
    TextView _TvResult;
    @InjectView(R.id.btStartThread)
    Button _BtStartThread;
    @InjectView(R.id.btStopThread)
    Button _BtStopThread;
    private Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.inject(this);

        ViewObservable.clicks(_BtStartThread, false)
                .subscribe((b) -> handleStartThreadClick());

        ViewObservable.clicks(_BtStopThread, false)
                .subscribe((v) -> {
                            Timber.d("Stop thread");
                            subscribe.unsubscribe();
                        }
                );

    }

    private void handleStartThreadClick() {
        subscribe = result().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((s) -> _TvResult.setText(s));

    }

    private Observable<String> result() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(5000);
                final String result = String.format("Result: %s", DateUtils.formatElapsedTime(System.currentTimeMillis()));
                Timber.d("result: %s", result);
                subscriber.onNext(result);
            }
        });
    }
}
