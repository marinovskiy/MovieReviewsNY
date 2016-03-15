package ua.marinovskiy.moviereviewsny.screens.fragments;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import ua.marinovskiy.moviereviewsny.R;
import ua.marinovskiy.moviereviewsny.models.db.Link;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.screens.activities.DetailsActivity;
import ua.marinovskiy.moviereviewsny.utils.DateUtils;
import ua.marinovskiy.moviereviewsny.utils.RealmManager;
import ua.marinovskiy.moviereviewsny.utils.Utils;

public class DetailsFragment extends BaseFragment {

    @Bind(R.id.big_poster)
    ImageView bigPoster;

    @Bind(R.id.small_poster)
    ImageView smallPoster;

    @Bind(R.id.tv_author)
    TextView tvAuthor;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.tv_rating)
    TextView tvRating;

    @Bind(R.id.tv_summary)
    TextView tvSummary;

    @Bind(R.id.ll_related_urls_container)
    LinearLayout llRelatedUrlsContainer;

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    CustomTabsClient mClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent customTabsIntent;

    private int id;

    private Review mReview;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(int id) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DetailsActivity.KEY_REVIEW_INDEX, id);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(DetailsActivity.KEY_REVIEW_INDEX);
        }
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName,
                                                     CustomTabsClient customTabsClient) {
                mClient = customTabsClient;
                mClient.warmup(0L);
                mCustomTabsSession = mClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getContext(), CUSTOM_TAB_PACKAGE_NAME,
                mCustomTabsServiceConnection);

        customTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setShowTitle(true)
                .setStartAnimations(getContext(), R.anim.slide_in_to_left, R.anim.do_nothing)
                .setExitAnimations(getContext(), R.anim.do_nothing, R.anim.slide_out_to_right)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSubscription(RealmManager.getById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUi, Throwable::printStackTrace));
    }

    @OnClick(R.id.btn_more)
    public void onClick() {
        customTabsIntent.launchUrl(getActivity(), Uri.parse(mReview.getLink().getUrl()));
    }

    private void updateUi(Review review) {
        mReview = review;
        Utils.loadImageWithBlurEffect(bigPoster, mReview.getMultimedia().getSrc());
        Utils.loadImage(smallPoster, mReview.getMultimedia().getSrc());
        tvAuthor.setText(mReview.getByLine());
        tvDate.setText(DateUtils.formatDate(mReview.getDateUpdated()));
        if (mReview.getMpaaRating() != null) {
            tvRating.setText(mReview.getMpaaRating());
        } else {
            tvRating.setText(R.string.text_no_rating);
        }
        tvSummary.setText(Html.fromHtml(mReview.getSummaryShort()));
        inflateRelated(review.getRelatedUrls());
    }

    private void inflateRelated(List<Link> relatedUrls) {
        llRelatedUrlsContainer.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (Link relatedUrl : relatedUrls) {
            TextView url = (TextView) layoutInflater.inflate(R.layout.related_item_layout,
                    llRelatedUrlsContainer, false);
            url.setText(relatedUrl.getType());
            llRelatedUrlsContainer.addView(url);
            url.setOnClickListener(v -> customTabsIntent.launchUrl(getActivity(),
                    Uri.parse(relatedUrl.getUrl())));
        }
    }
}
