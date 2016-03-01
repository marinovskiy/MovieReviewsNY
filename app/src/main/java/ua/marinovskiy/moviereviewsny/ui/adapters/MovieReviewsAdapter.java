package ua.marinovskiy.moviereviewsny.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.utils.Utils;

/**
 * Created by Alex on 29.02.2016.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    private List<Review> mReviews;

    public MovieReviewsAdapter(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindReview(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_movie_poster)
        ImageView mIvPoster;

        @Bind(R.id.tv_movie_title)
        TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindReview(Review review) {
            Utils.loadImage(mIvPoster, review.getMultimedia().getResources().getSrc());
            mTvTitle.setText(review.getDisplayTitle());
            Log.i("ADAPTERLOGTAG", "bindReview: " + review.getDisplayTitle());
        }

    }

}
