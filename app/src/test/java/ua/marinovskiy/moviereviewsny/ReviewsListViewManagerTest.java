package ua.marinovskiy.moviereviewsny;

import android.test.mock.MockApplication;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.marinovskiy.moviereviewsny.screens.fragments.BaseFragment;
import ua.marinovskiy.moviereviewsny.screens.fragments.ReviewsListViewManager;
import ua.marinovskiy.moviereviewsny.screens.views.ReviewsListView;

import static org.mockito.Mockito.verify;

/**
 * Created by Alex on 14.03.2016.
 */
public class ReviewsListViewManagerTest {

    @Mock
    ReviewsListView reviewsListView;

    @Mock
    BaseFragment baseFragment;

    private ReviewsListViewManager mReviewsListViewManager;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        mReviewsListViewManager = new ReviewsListViewManager(reviewsListView, baseFragment);
    }

    @Test
    public void validateInitialize() {
        mReviewsListViewManager.initialize();
        verify(mReviewsListViewManager).getMoviesReviewsFromDb();
        verify(mReviewsListViewManager).getMoviesReviewsFromNetwork();
    }

}
