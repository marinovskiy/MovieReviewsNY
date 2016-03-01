package ua.marinovskiy.moviereviewsny.screens.activities;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Alex on 29.02.2016.
 */
public class BaseActivity extends AppCompatActivity {

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
