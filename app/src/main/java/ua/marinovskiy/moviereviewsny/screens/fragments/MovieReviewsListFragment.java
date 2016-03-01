package ua.marinovskiy.moviereviewsny.screens.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.api.ApiManager;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.models.network.NetworkReview;
import ua.marinovskiy.moviereviewsny.ui.adapters.MovieReviewsAdapter;
import ua.marinovskiy.moviereviewsny.utils.ModelConverter;

public class MovieReviewsListFragment extends BaseFragment {

    public static final String TAG = "MovieReviewsListFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public MovieReviewsListFragment() {

    }

    public static MovieReviewsListFragment newInstance() {
        return new MovieReviewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_reviews_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMoviesReviews();
    }

    private void getMoviesReviews() {
        addSubscription(
                ApiManager.getInstance()
                        .allResponses()
                        .map(response -> {
                            List<Review> reviews = new ArrayList<>();
                            for (NetworkReview networkReview : response.getReviews()) {
                                reviews.add(ModelConverter.convertToReview(networkReview));
                            }
                            Log.i(TAG, "getMoviesReviews: " + reviews.size());
                            Log.i(TAG, "getMoviesReviews: " + reviews.get(0).getDisplayTitle());
                            return reviews;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(MovieReviewsListFragment.this::updateUi,
                                Throwable::printStackTrace));
    }

    private void updateUi(List<Review> reviews) {
        MovieReviewsAdapter adapter = new MovieReviewsAdapter(reviews);
        mRecyclerView.setAdapter(adapter);
    }

}
