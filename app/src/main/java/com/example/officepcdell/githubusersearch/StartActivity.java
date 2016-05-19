package com.example.officepcdell.githubusersearch;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.officepcdell.githubusersearch.adapters.RecyclerAdapter;
import com.example.officepcdell.githubusersearch.classes.APIError;
import com.example.officepcdell.githubusersearch.classes.SearchResponse;
import com.example.officepcdell.githubusersearch.interfaces.githubServiceClass;
import com.example.officepcdell.githubusersearch.networks.ErrorUtils;
import com.example.officepcdell.githubusersearch.networks.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * check the internet connection
         */
        if (!connectInternet()) {
            Toast.makeText(this, R.string.check_internet, Toast.LENGTH_LONG).show();
        }
    }
    public boolean connectInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting()); //return true or false
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener () {
                    @Override
                    public boolean onQueryTextChange(String newText){
                        System.out.println(newText);
                        if (!newText.trim().isEmpty()){ SetSearchQuery(newText);}
                        return false;
                    }
                    @Override
                    public boolean onQueryTextSubmit(String query){
                        System.out.println(query);
                        return true;
                    }
                }
            );
        return true;
    }

    private void SetSearchQuery(String newText) {
        githubServiceClass service = ServiceGenerator.createService(githubServiceClass.class);
        Call<SearchResponse> call = service.requestUserList(newText);
        call.enqueue(callback());
    }

    private Callback<SearchResponse> callback() {
        return new Callback<SearchResponse>(){
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    getDataSet(response);
                }
                else{
                    APIError error = ErrorUtils.parseError(response);
                    String toastString = getString(R.string.error_message) + error.message()+":"+ error.statusCode();
                    Toast.makeText(getApplicationContext(),toastString, Toast.LENGTH_LONG).show();
                    System.out.println("Error, not succesfull: "+ error.message());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("Error on failure", t.getMessage());
            }
        };
    }

    private void getDataSet(Response<SearchResponse> response) {
        ArrayList<String> scripts = new ArrayList<>();
        List<SearchResponse.ItemsBean> items = response.body().getItems();
        for (SearchResponse.ItemsBean item : items) {
            String loginitem = item.getLogin();
            scripts.add(loginitem);
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);
        assert mRecyclerView != null;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new RecyclerAdapter(scripts);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
