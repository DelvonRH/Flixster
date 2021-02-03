package com.example.flixster.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ItemMovieBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

@Parcel
public class Movie
{
    int movieID;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;

    // empty constructor needed by Parceler library
    public Movie()
    {

    }

    public Movie(JSONObject jsonObject) throws JSONException
    {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJSONArray.length(); i++)
        {
            movies.add(new Movie(movieJSONArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle()
    {
        return title;
    }

    public String getOverview()
    {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieID()
    {
        return movieID;
    }

    @BindingAdapter("getImagePath")
    public static void getImgURL(ImageView imageView, String url)
    {
        Glide.with(imageView.getContext()).load(url).transform(new RoundedCornersTransformation(50,10)).into(imageView);
    }
}
