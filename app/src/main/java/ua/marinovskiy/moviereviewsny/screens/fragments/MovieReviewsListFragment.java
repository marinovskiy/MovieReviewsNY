package ua.marinovskiy.moviereviewsny.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.interfaces.ListFragmentCallback;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.screens.views.ReviewsListView;
import ua.marinovskiy.moviereviewsny.ui.adapters.MovieReviewsAdapter;
import ua.marinovskiy.moviereviewsny.ui.listeners.OnItemClickListener;
import ua.marinovskiy.moviereviewsny.utils.Utils;

public class MovieReviewsListFragment extends BaseFragment
        implements ReviewsListView {

    public static final String TAG = "MovieReviewsListFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Bind(R.id.swipe_reload)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ReviewsListViewManager mReviewsListViewManager;

    private ListFragmentCallback mListFragmentCallback;

    private boolean mIsAnimated = false;

    private boolean mIsAnimating = false;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mReviewsListViewManager = new ReviewsListViewManager(this, this);
        mListFragmentCallback = (ListFragmentCallback) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(Utils.isLand(getResources()) ?
                new GridLayoutManager(getContext(), 2) : new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReviewsListViewManager.initialize();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            refresh();
        });
    }

    @Override
    public void onDetach() {
        mReviewsListViewManager.destroy();
        mListFragmentCallback = null;
        super.onDetach();
    }

    private void updateUi(List<Review> reviews) {
        MovieReviewsAdapter adapter;
        if (mRecyclerView.getAdapter() == null) {
            adapter = new MovieReviewsAdapter(reviews);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnClickListener(onItemClickListener);
            if (!mIsAnimated) {
                mRecyclerView.getViewTreeObserver()
                        .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                mIsAnimating = true;
                                mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                                    mRecyclerView.getChildAt(i).setTranslationX(mRecyclerView.getWidth());
                                    ViewPropertyAnimator animate = mRecyclerView.getChildAt(i).animate();
                                    if (i == mRecyclerView.getChildCount() - 1) {
                                        animate.withEndAction(() -> {
                                            mIsAnimating = false;
                                            adapter.notifyDataSetChanged();
                                        });
                                    }
                                    animate.setDuration(600).setStartDelay(i * 100).translationX(0).start();
                                }
                                return true;
                            }
                        });
            }
        } else {
            adapter = (MovieReviewsAdapter) mRecyclerView.getAdapter();
            adapter.updateList(reviews);
            if (!mIsAnimating) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private final OnItemClickListener onItemClickListener = (view, id) -> {
        if (mListFragmentCallback != null) {
            mListFragmentCallback.onReviewClick(id);
        }
    };

    @Override
    public void showLoader() {
        if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0) {
            return;
        }
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRetry() {
        Utils.showRetryDialog(getContext(), R.string.retry_hint, (dialog, which) -> {
            mReviewsListViewManager.reload();
        });
    }

    @Override
    public void hideRetry() {
        // TODO btn in layout
    }

    @Override
    public void refresh() {
        mReviewsListViewManager.reload();
    }

    @Override
    public void renderList(List<Review> reviews) {
        hideLoader();
        updateUi(reviews);
        if (mListFragmentCallback != null) {
            mListFragmentCallback.onReviewsLoaded(reviews);
        }
    }
}
