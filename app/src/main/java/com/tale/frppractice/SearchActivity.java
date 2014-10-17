package com.tale.frppractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by TALE on 10/15/2014.
 * <p>
 * Demo how to use RxAndroid to implement search feature.
 * <p>
 * Say requirement is: We have a text field. When user enter text in this text field, we will
 * automatic do search for the entered text. For optimize reason, we won't call search function
 * for every single letter that they entered, but wait for they typing and after they stop typing
 * about 400ms we will call our search function.
 * while use is typing but delay for about 400ms
 */
public class SearchActivity extends ActionBarActivity {

    @InjectView(R.id.etSearch)
    EditText _EtSearch;
    @InjectView(R.id.llSearchQueries)
    LinearLayout _LlSearchQueries;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        final Observable<String> searchTextObservable = ViewObservable.input(_EtSearch, false)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter((s) -> !TextUtils.isEmpty(s))
                .observeOn(AndroidSchedulers.mainThread());
        subscription = searchTextObservable
                .subscribe((s) -> {
                    final TextView queryView = newQueryView(s);
                    _LlSearchQueries.addView(queryView);
                });
    }

    TextView newQueryView(String text) {
        Timber.d("newQueryView=> text: %s", text);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        return textView;
    }
}
