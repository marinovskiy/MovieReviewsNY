package ua.marinovskiy.moviereviewsny.screens.activities;

import android.os.Bundle;

import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.screens.fragments.MovieReviewsListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container,
                            MovieReviewsListFragment.newInstance(),
                            MovieReviewsListFragment.TAG)
                    .commit();
        }
    }
}
