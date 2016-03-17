package ua.marinovskiy.moviereviewsny.screens.views;

import java.util.List;

import ua.marinovskiy.moviereviewsny.models.db.Review;

/**
 * Created by Alex on 02.03.2016.
 */
public interface ReviewsListView {

    void showLoader();

    void hideLoader();

    void showErrorMessage(String message);

    void showRetry();

    void refresh();

    void renderList(List<Review> reviews);
}
