package ua.marinovskiy.moviereviewsny.screens.activities;

import android.os.Bundle;

import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.screens.fragments.DetailsFragment;

public class DetailsActivity extends BaseActivity {

    public static final String KEY_REVIEW_ID = "key_review_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            int id = getIntent().getIntExtra(KEY_REVIEW_ID, 0);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_container,
                            DetailsFragment.newInstance(id),
                            DetailsFragment.class.getSimpleName())
                    .commit();
        }

    }
}
