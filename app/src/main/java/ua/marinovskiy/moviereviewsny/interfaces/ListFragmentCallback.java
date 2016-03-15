package ua.marinovskiy.moviereviewsny.interfaces;

import java.util.List;

import ua.marinovskiy.moviereviewsny.models.db.Review;

/**
 * Created by Alex on 09.03.2016.
 */
public interface ListFragmentCallback {

    void onReviewClick(int position);

    void onReviewsLoaded(List<Review> reviews);

}
