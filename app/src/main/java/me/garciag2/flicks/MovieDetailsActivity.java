package me.garciag2.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.garciag2.flicks.models.Config;
import me.garciag2.flicks.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    Config config;

    TextView tvTitle;
    ImageView ivBackdrop;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        config = (Config) Parcels.unwrap(getIntent().getParcelableExtra(Config.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //load image using glide

        int placeholderId = R.drawable.flicks_backdrop_placeholder;

        ImageView ivBackdropImage = findViewById(R.id.ivBackdrop);

        String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());

        Glide.with(this)
                .load(imageUrl)
                .apply(
                        RequestOptions.placeholderOf(placeholderId)
                                .error(placeholderId)
                                .fitCenter()
                )
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0))).into(ivBackdropImage);





        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}
