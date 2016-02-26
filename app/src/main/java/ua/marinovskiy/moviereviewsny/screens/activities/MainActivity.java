package ua.marinovskiy.moviereviewsny.screens.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.api.ApiManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "logtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiManager
                .getInstance()
                .allResponses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        Log.d(TAG, "onCreate: " + response.getReviews().size());
                    }
                }, Throwable::printStackTrace);
    }
}
