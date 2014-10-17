package com.tale.frppractice;

import rx.Observable;

/**
 * Created by TALE on 10/14/2014.
 */
public class Practice {

    private static Observable<String> listString;

    public static void main(String[] args) {
        final String[] strings = {"hell", "world"};
        listString = Observable.from(strings);
        listString.subscribe((s) -> System.out.println(s));
        listString.flatMap((s) -> Observable.from(s.length(), s.length() * 2))
                .take(1)
                .subscribe((length) -> System.out.println(length));
//        System.out.println("Hello world");
    }
}
