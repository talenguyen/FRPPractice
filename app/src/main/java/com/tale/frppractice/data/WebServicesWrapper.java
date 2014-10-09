package com.tale.frppractice.data;

import com.tale.frppractice.data.pojo.People;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by TALE on 9/5/2014.
 */
public class WebServicesWrapper implements WebServices {

    private final WebServices webServices;

    public WebServicesWrapper() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WebServices.BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        webServices = restAdapter.create(WebServices.class);
    }

    @Override
    public Observable<List<People>> getUsers(@Query("since") long since) {
        return webServices.getUsers(since);
    }
}
