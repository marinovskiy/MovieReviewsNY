package ua.marinovskiy.moviereviewsny.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.BlurTransformation;
import ua.marinovskiy.moviereviewsny.R;

/**
 * Created by Alex on 29.02.2016.
 */
public class Utils {

    public static boolean hasInternet(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean hasApi21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void loadImage(ImageView imageView, String url) {
        if (imageView == null || imageView.getContext() == null
                || imageView.getContext().isRestricted()) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void loadImageWithBlurEffect(ImageView imageView, String url) {
        if (imageView == null || imageView.getContext() == null
                || imageView.getContext().isRestricted()) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(url)
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 4))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void showRetryDialog(Context context, @StringRes int message,
                                       @Nullable DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.btn_retry, onClickListener)
                .setNegativeButton(R.string.btn_close, onClickListener)
                .create();
        alertDialog.show();
    }

    public static boolean isLand(Resources resources) {
        return resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
