package ua.marinovskiy.moviereviewsny.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Alex on 29.02.2016.
 */
public class Utils {

    public static void loadImage(ImageView imageView, String url) {
        if (imageView == null || imageView.getContext() == null
                || imageView.getContext().isRestricted()) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

}
