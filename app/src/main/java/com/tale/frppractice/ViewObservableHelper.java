package com.tale.frppractice;

import android.view.View;
import android.view.ViewTreeObserver;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by TALE on 10/1/2014.
 */
public class ViewObservableHelper {

    public static Observable<View> globalLayoutFrom(final View view) {
        final PublishSubject<View> subject = PublishSubject.create();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                subject.onNext(view);
                subject.onCompleted();
            }
        });
        return subject;
    }

    public static Observable<View> longClick(View view) {
        final PublishSubject<View> subject = PublishSubject.create();
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                subject.onNext(v);
                return true;
            }
        });
        return subject;
    }

    public static <T extends View> Observable<T> clickFrom(T view) {
        final PublishSubject publishSubject = PublishSubject.create();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishSubject.onNext(v);
            }
        });
        return publishSubject.asObservable();
    }
}
