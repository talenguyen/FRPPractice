package com.tale.frppractice.data;

import com.tale.frppractice.data.pojo.People;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by TALE on 9/5/2014.
 */
public interface WebServices {

    public static final String BASE_URL = "https://api.github.com";
    @GET("/users")
    Observable<List<People>> getUsers(@Query("since") long since);
}
