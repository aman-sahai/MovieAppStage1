package com.example.study.movieapp1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.study.movieapp1.Model.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<Model> modelList;

    private static final String apikey=BuildConfig.API;

    private static final String popular_url = "http://api.themoviedb.org/3/movie/popular?api_key="+apikey;
    private static final String top_rated_url = "http://api.themoviedb.org/3/movie/top_rated?api_key="+apikey;

    public static final String res="results";
    public static final String ii="id";
    public static final String va="vote_average";
    public static final String p_p="poster_path";
    public static final String  o_t="original_title";
    public static final String over="overview";
    public static final String rd="release_date";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        datafetch_popular();
    }

    public void datafetch_popular() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, popular_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelList = new ArrayList<>();
                Model model = new Model();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(res);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int id = jsonObject1.optInt(ii,1);
                        Double vote_average = jsonObject1.optDouble(va,0.0);
                        String poster_path = jsonObject1.optString(p_p,getResources().getString(R.string.nopos));
                        String original_title = jsonObject1.optString(o_t,getResources().getString(R.string.notit));
                        String overview = jsonObject1.optString(over,getResources().getString(R.string.noover));
                        String release_date = jsonObject1.optString(rd,getResources().getString(R.string.nord));
                        model.setOriginal_title(original_title);
                        model.setVote_average(vote_average);
                        model.setPoster_path(poster_path);
                        model.setOverview(overview);
                        model.setRelease_date(release_date);
                        Model m = new Model(id, vote_average, poster_path, original_title, overview, release_date);
                        modelList.add(m);
                        movieAdapter = new MovieAdapter(MainActivity.this, modelList);
                        recyclerView.setAdapter(movieAdapter);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        }
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), ""+getString(R.string.error_cause) + error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void datafetch_toprated() {
        StringRequest request = new StringRequest(Request.Method.GET, top_rated_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelList = new ArrayList<>();
                Model model = new Model();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(res);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int id = jsonObject1.optInt(ii);
                        Double vote_average = jsonObject1.optDouble(va,0.0);
                        String poster_path = jsonObject1.optString(p_p,getResources().getString(R.string.nopos));
                        String original_title = jsonObject1.optString(o_t,getResources().getString(R.string.notit));
                        String overview = jsonObject1.optString(over,getResources().getString(R.string.noover));
                        String release_date = jsonObject1.optString(rd,getResources().getString(R.string.nord));
                        model.setOriginal_title(original_title);
                        model.setVote_average(vote_average);
                        model.setPoster_path(poster_path);
                        model.setOverview(overview);
                        model.setRelease_date(release_date);
                        Model m = new Model(id, vote_average, poster_path, original_title, overview, release_date);
                        modelList.add(m);
                        movieAdapter = new MovieAdapter(MainActivity.this, modelList);
                        recyclerView.setAdapter(movieAdapter);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        }
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), ""+getString(R.string.error_cause), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.top_rated:
                datafetch_toprated();
                break;
            case R.id.popular:
                datafetch_popular();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
