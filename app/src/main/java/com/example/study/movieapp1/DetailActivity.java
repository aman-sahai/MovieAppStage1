package com.example.study.movieapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView poster_image;
    private TextView movie_title, userrating, synopsis, releasedate;

    private static final String ps="plot_synopsis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        poster_image = findViewById(R.id.poster_image);
        movie_title = findViewById(R.id.movie_title);
        userrating = findViewById(R.id.userrating);
        synopsis = findViewById(R.id.synopsis);
        releasedate = findViewById(R.id.releasedate);
        setValue();
    }

    private void setValue() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.o_t);
        String poster = intent.getStringExtra(MainActivity.p_p);
        String release = intent.getStringExtra(MainActivity.rd);
        String plotsynopsis = intent.getStringExtra(ps);
        Double vote = intent.getDoubleExtra(MainActivity.va, 0.0);
        Picasso.with(this)
                .load(poster)
                .placeholder(R.drawable.loading)
                .into(poster_image);
        movie_title.setText(title);
        synopsis.setText(plotsynopsis);
        releasedate.setText(release);
        userrating.setText(String.valueOf(vote));
    }
}
