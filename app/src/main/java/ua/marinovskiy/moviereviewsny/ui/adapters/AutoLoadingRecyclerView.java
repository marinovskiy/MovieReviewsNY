package ua.marinovskiy.moviereviewsny.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscription;
import rx.subjects.PublishSubject;
import ua.marinovskiy.moviereviewsny.utils.RxUtils;

public class AutoLoadingRecyclerView extends RecyclerView {

    private static final String TAG = "AutoLoadingRecyclerView";

    private OffsetLimit mOffsetLimit;
    private Subscription mSubscription;
    private final PublishSubject<OffsetLimit> mPublishSubject = PublishSubject.create();

    @Nullable
    private OnLoadMoreListener mOnLoadMoreListener;

    public AutoLoadingRecyclerView(Context context) {
        super(context);
        init();
    }

    public AutoLoadingRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoLoadingRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOffsetLimit(int offsetLimit, int limit) {
        this.mOffsetLimit = new OffsetLimit(offsetLimit, limit);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDetachedFromWindow();
    }

    public void resetOffset() {
        if (mOffsetLimit != null) {
            mOffsetLimit.setOffset(0);
        }
    }

    private void init() {
        if (isInEditMode()) return;
        mOffsetLimit = new OffsetLimit(0, 20);
        addOnScrollListener(onScrollListener);
        mSubscription = mPublishSubject.asObservable()
                .compose(RxUtils.applySchedulers())
                .distinctUntilChanged(OffsetLimit::getOffset)
                .subscribe(mOffsetLimit -> {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore(mOffsetLimit);
                    }
                    Log.d(TAG, mOffsetLimit.toString());
                });
    }

    private final OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition == NO_POSITION) {
                return;
            }
            int limit = mOffsetLimit.getLimit();
            int itemsCount = getAdapter().getItemCount();
            int updatePosition = itemsCount - (limit / 2);
            if (lastVisiblePosition >= updatePosition) {
                mOffsetLimit.setOffset(itemsCount);
                mPublishSubject.onNext(mOffsetLimit);
            }
        }
    };

    private int getLastVisiblePosition() {
        int position;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

            int[] into = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            List<Integer> intoList = new ArrayList<>();
            for (int i : into) {
                intoList.add(i);
            }
            position = Collections.max(intoList);
        } else {
            throw new RuntimeException("Unknown LayoutManager class: " + layoutManager.getClass().getSimpleName());
        }
        return position;
    }

    public void setOnLoadMoreListener(int limit, @Nullable OnLoadMoreListener onLoadMoreListener) {
        setOffsetLimit(0, limit);
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(OffsetLimit offsetLimit);
    }

}
