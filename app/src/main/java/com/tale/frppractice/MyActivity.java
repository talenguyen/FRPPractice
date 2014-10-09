package com.tale.frppractice;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tale.frppractice.adapter.PeopleAdapter;
import com.tale.frppractice.data.WebServices;
import com.tale.frppractice.data.WebServicesWrapper;
import com.tale.frppractice.data.pojo.People;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;


public class MyActivity extends ActionBarActivity {

    private Picasso picasso;
    private String TAG = MyActivity.class.getSimpleName();
    private PeopleAdapter peopleAdapter;
    private ListView lvPeople;
    private WebServices webServices;

    private Observable<View> refreshClickStream;
    private Observable<List<People>> responseStream;
    private Observable<Long> requestStream;
    private Observable<People> suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initData();
        initStream();
        initUi();
    }

    private void initStream() {
        requestStream = ViewObservableHelper.clickFrom(findViewById(R.id.btRefresh))
                .map(view -> System.currentTimeMillis() % 500)
                .startWith(0l);
        responseStream = requestStream.flatMap((since) -> webServices.getUsers(since));
        suggestion = responseStream.map((people) -> {
            final int index = (int) (System.currentTimeMillis() % people.size());
            return people.get(index);
        });
    }

    private void initUi() {
        peopleAdapter = new PeopleAdapter(getApplication(), picasso);
        lvPeople = ((ListView) findViewById(R.id.lvPeople));
        lvPeople.setAdapter(peopleAdapter);
        suggestion.subscribe((person) -> peopleAdapter.add(person));
    }

    private void initData() {
        picasso = new Picasso.Builder(getApplication())
                .listener((picasso, uri, e) -> Log.e(TAG, "Failed to load image: " + uri))
                .downloader(new OkHttpDownloader(new OkHttpClient()))
                .build();
        webServices = new WebServicesWrapper();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
