package com.example.study.movieapp1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.study.movieapp1.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context context;
    List<Model> movieList;

    public MovieAdapter(Context context, List<Model> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(context)
                .inflate(R.layout.movie_list, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(v);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.movie_title.setText(movieList.get(i).getOriginal_title());
        String vote = Double.toString(movieList.get(i).getVote_average());
        movieViewHolder.user_rating.setText(vote);
        Picasso.with(context)
                .load(movieList.get(i).getPoster_path())
                .placeholder(R.drawable.loading)
                .into(movieViewHolder.movie_poster_image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movie_poster_image;
        TextView movie_title, user_rating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster_image = itemView.findViewById(R.id.movie_poster_image);
            movie_title = itemView.findViewById(R.id.title);
            user_rating = itemView.findViewById(R.id.user_rating);
            movie_poster_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id",movieList.get(position).getId());
            intent.putExtra("original_title", movieList.get(position).getOriginal_title());
            intent.putExtra("vote_average", movieList.get(position).getVote_average());
            intent.putExtra("poster_path", movieList.get(position).getPoster_path());
            intent.putExtra("release_date", movieList.get(position).getRelease_date());
            intent.putExtra("plot_synopsis", movieList.get(position).getOverview());
            context.startActivity(intent);
        }
    }
}
