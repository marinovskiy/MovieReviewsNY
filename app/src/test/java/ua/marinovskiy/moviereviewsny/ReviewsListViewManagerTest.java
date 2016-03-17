package ua.marinovskiy.moviereviewsny;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import rx.Observable;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.screens.fragments.BaseFragment;
import ua.marinovskiy.moviereviewsny.screens.fragments.ReviewsListViewManager;
import ua.marinovskiy.moviereviewsny.screens.views.ReviewsListView;
import ua.marinovskiy.moviereviewsny.utils.Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Alex on 14.03.2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmResults.class, RealmCore.class})
public class ReviewsListViewManagerTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();
    Realm mockRealm;

    @Mock
    ReviewsListView reviewsListView;

    @Mock
    BaseFragment baseFragment;

    private ReviewsListViewManager mReviewsListViewManager;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        mockStatic(Realm.class);

        Realm mockRealm = PowerMockito.mock(Realm.class);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        this.mockRealm = mockRealm;

        mockStatic(Utils.class);

        mReviewsListViewManager = new ReviewsListViewManager(reviewsListView, baseFragment);
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
    }

    @Test
    public void validateInitialize() {
        RealmQuery<Review> realmQuery = mock(RealmQuery.class);
        when(mockRealm.where(Review.class)).thenReturn(realmQuery);
        RealmResults<Review> realmResults = mock(RealmResults.class);
        when(realmQuery.findAllAsync()).thenReturn(realmResults);
        when(realmResults.asObservable()).thenReturn(Observable.just(realmResults));



        mReviewsListViewManager.initialize();
//        verify(reviewsListView).renderList(realmResults);
        verify(reviewsListView, times(2)).hideLoader();
//        verify(baseFragment, never()).addSubscription(mock(Subscription.class));
        //verify(mReviewsListViewManager).getMoviesReviewsFromNetwork();
    }

}
