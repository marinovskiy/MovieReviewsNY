package ua.marinovskiy.moviereviewsny.screens.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.screens.fragments.DetailsFragment;
import ua.marinovskiy.moviereviewsny.ui.adapters.DetailsPagerAdapter;
import ua.marinovskiy.moviereviewsny.utils.RealmManager;

public class DetailsActivity extends BaseActivity {

    @Bind(R.id.toolbar_details)
    Toolbar mToolbar;

    @Bind(R.id.details_vp)
    ViewPager mViewPager;

    private List<String> mTitles = new ArrayList<>();

    public static final String KEY_REVIEW_INDEX = "key_review_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_arrow_back_white));
        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        int index = getIntent().getIntExtra(KEY_REVIEW_INDEX, 0);

        addSubscription(RealmManager.allReviews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    DetailsPagerAdapter pagerAdapter =
                            new DetailsPagerAdapter(getSupportFragmentManager(),
                                    createPages(list));
                    mViewPager.setAdapter(pagerAdapter);
                    mViewPager.setCurrentItem(index);
                }, Throwable::printStackTrace));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(mTitles.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<Fragment> createPages(List<Review> reviewList) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0, reviewListSize = reviewList.size(); i < reviewListSize; i++) {
            Review review = reviewList.get(i);
            fragments.add(DetailsFragment.newInstance(review.getMovieId()));
            mTitles.add(review.getDisplayTitle());
        }
        return fragments;
    }
}
