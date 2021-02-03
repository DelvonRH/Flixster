package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity
{
    private static final String YOUTUBE_API_KEY = "AIzaSyD1kMnI6cBXfiC3m15Ssyng_UD26miRnR0";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    Movie movie;
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        binding.setMovie(movie);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int i, Headers headers, JSON json)
            {
                try
                {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0)
                    {
                        return;
                    }
                    String youtubekey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubekey);
                    initializeYoutube(youtubekey);
                }
                catch (JSONException e)
                {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });
   }

    private void initializeYoutube(final String youtubekey)
    {
        binding.player.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                Log.d("DetailActivity", "onInitializationSuccess");
                youTubePlayer.cueVideo(youtubekey);
                if(movie.getRating() > 5.0)
                {
                    youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                    youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

                    youTubePlayer.setFullscreen(true);
                    youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {

                        }

                        @Override
                        public void onLoaded(String s) {
                            youTubePlayer.play();
                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {

                        }

                        @Override
                        public void onVideoEnded() {

                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {

                        }
                    });
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
            {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    }
}