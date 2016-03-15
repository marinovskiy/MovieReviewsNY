package ua.marinovskiy.moviereviewsny.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.interfaces.ListFragmentCallback;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.screens.fragments.DetailsFragment;
import ua.marinovskiy.moviereviewsny.screens.fragments.MovieReviewsListFragment;
import ua.marinovskiy.moviereviewsny.ui.adapters.DetailsPagerAdapter;

public class MainActivity extends BaseActivity implements ListFragmentCallback {

    @Nullable
    @Bind(R.id.details_vp)
    ViewPager mViewPager;

    @Nullable
    @Bind(R.id.empty_screen)
    LinearLayout mEmptyScreen;

    @Nullable
    @Bind(R.id.toolbar_main)
    Toolbar mToolbarMainPort;

    @Nullable
    @Bind(R.id.toolbar_details_land)
    Toolbar mToolbarDetailsLand;

    private List<Review> mReviews;

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
        if (isTabled() && mToolbarDetailsLand != null) {
            setSupportActionBar(mToolbarDetailsLand);
        } else if (mToolbarMainPort != null) {
            setSupportActionBar(mToolbarDetailsLand);
        }
    }

    @Override
    public void onReviewClick(int position) {
        if (isTabled()) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(position, true);
            }
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.KEY_REVIEW_INDEX, position);
            startActivity(intent);
        }
    }

    @Override
    public void onReviewsLoaded(List<Review> reviews) {
        mReviews = reviews;
        updateViewPager();
        if (mReviews != null && !mReviews.isEmpty() && mEmptyScreen != null) {
            mEmptyScreen.setVisibility(View.GONE);
        }
    }

    public boolean isTabled() {
        return mViewPager != null;
    }

    private void updateViewPager() {
        if (mReviews != null) {
            DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager(),
                    createPages(mReviews));
            if (mViewPager != null) {
                mViewPager.setAdapter(adapter);
            }
        }
    }

    private List<Fragment> createPages(List<Review> reviewList) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0, reviewListSize = reviewList.size(); i < reviewListSize; i++) {
            Review review = reviewList.get(i);
            fragments.add(DetailsFragment.newInstance(review.getMovieId()));
        }
        return fragments;
    }

}