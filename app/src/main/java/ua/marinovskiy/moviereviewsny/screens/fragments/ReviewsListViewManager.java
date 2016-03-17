package ua.marinovskiy.moviereviewsny.screens.fragments;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.marinovskiy.moviereviewsny.api.ApiManager;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.models.network.NetworkReview;
import ua.marinovskiy.moviereviewsny.screens.views.ReviewsListView;
import ua.marinovskiy.moviereviewsny.utils.ModelConverter;
import ua.marinovskiy.moviereviewsny.utils.RealmManager;
import ua.marinovskiy.moviereviewsny.utils.Utils;

/**
 * Created by Alex on 02.03.2016.
 */
public class ReviewsListViewManager {

    private ReviewsListView mReviewsListView;
    private BaseFragment mBaseFragment;

    public ReviewsListViewManager(ReviewsListView reviewsListView, BaseFragment baseFragment) {
        mReviewsListView = reviewsListView;
        mBaseFragment = baseFragment;
    }

    public void initialize() {
        getMoviesReviewsFromDb();
        getMoviesReviewsFromNetwork();
    }

    public void reload() {
        initialize();
    }

    public void destroy() {
        mReviewsListView = null;
        mBaseFragment = null;
    }

    public void getMoviesReviewsFromDb() {
        mBaseFragment.addSubscription(
                RealmManager.allReviews()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mReviewsListView::renderList,
                                throwable -> mReviewsListView.showErrorMessage(throwable.getMessage()),
                                mReviewsListView::hideLoader)
        );
    }

    public void getMoviesReviewsFromNetwork() {
        if (Utils.hasInternet(mBaseFragment.getContext())) {
            mReviewsListView.showLoader();
            mBaseFragment.addSubscription(
                    ApiManager.getInstance()
                            .allResponses()
                            .map(response -> {
                                List<Review> reviews = new ArrayList<>();
                                for (NetworkReview networkReview : response.getReviews()) {
                                    Review object = ModelConverter.convertToReview(networkReview);
                                    if (object != null) {
                                        object.getMultimedia().setMovieReviewId(networkReview.getNytMovieId());
                                    }
                                    reviews.add(object);
                                }
                                return reviews;
                            })
                            .doOnNext(RealmManager::save)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(mReviewsListView::renderList,
                                    throwable -> {
                                        mReviewsListView.showErrorMessage(throwable.getMessage());
                                        mReviewsListView.hideLoader();
                                        mReviewsListView.showRetry();
                                    },
                                    mReviewsListView::hideLoader));
        } else {
            mReviewsListView.hideLoader();
        }
    }

}
