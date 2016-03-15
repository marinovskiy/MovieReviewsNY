package ua.marinovskiy.moviereviewsny.ui.adapters;

import android.graphics.Bitmap;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.ui.listeners.OnItemClickListener;
import ua.marinovskiy.moviereviewsny.utils.Utils;

/**
 * Created by Alex on 29.02.2016.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    private List<Review> mReviews;

    private OnItemClickListener mClickListener;

    public MovieReviewsAdapter(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false));
    }

    public void updateList(List<Review> list) {
        mReviews = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindReview(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unSubscribe();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.iv_movie_poster)
        ImageView mIvPoster;

        @Bind(R.id.tv_movie_title)
        TextView mTvTitle;

        @Bind(R.id.tv_movie_rating)
        TextView mTvMovieRating;

        @Bind(R.id.tv_movie_author)
        TextView mTvMovieAuthor;

        private Review mReview;

        private Subscription mSubscription;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        private void bindReview(Review review) {
            mReview = review;
            mTvTitle.setText(mReview.getDisplayTitle());
            mTvMovieAuthor.setText(mReview.getByLine());
            if (mReview.getMpaaRating() != null) {
                mTvMovieRating.setText(mReview.getMpaaRating());
            } else {
                mTvMovieRating.setText(R.string.text_no_rating);
            }
            Utils.loadImage(mIvPoster, mReview.getMultimedia().getSrc());
        }

        public void unSubscribe() {
            if (mSubscription != null) {
                mSubscription.unsubscribe();
            }
        }
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        mClickListener = onClickListener;
    }

}
