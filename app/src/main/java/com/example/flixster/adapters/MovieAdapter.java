package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater movieView = LayoutInflater.from(context);
        ItemMovieBinding binding = ItemMovieBinding.inflate(movieView, parent, false);
        return new ViewHolder(binding);
    }

    // Involves populating data into th item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ItemMovieBinding binding;

        public ViewHolder(@NonNull ItemMovieBinding itemView)
        {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Movie movie)
        {
            binding.setMovie(movie);
            String imgURL;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imgURL = back drop image
                imgURL = movie.getBackdropPath();
            } else {
                // else imgURL = poster image
                imgURL = movie.getPosterPath();
            }
            binding.setImageUrl(imgURL);
            if(movie.getRating() > 5.0)
            {
                binding.playButton.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.playButton.setVisibility(View.GONE);
            }

            // 1. Register click listener on the whole row
            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((AppCompatActivity) context, (View)binding.ivPoster, "profile");
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
