package ua.marinovskiy.moviereviewsny.utils;

import android.support.annotation.Nullable;

import java.util.List;

import io.realm.RealmList;
import ua.marinovskiy.moviereviewsny.models.db.Link;
import ua.marinovskiy.moviereviewsny.models.db.Multimedia;
import ua.marinovskiy.moviereviewsny.models.db.Resource;
import ua.marinovskiy.moviereviewsny.models.db.Review;
import ua.marinovskiy.moviereviewsny.models.network.NetworkLink;
import ua.marinovskiy.moviereviewsny.models.network.NetworkMultimedia;
import ua.marinovskiy.moviereviewsny.models.network.NetworkResource;
import ua.marinovskiy.moviereviewsny.models.network.NetworkReview;

/**
 * Created by Alex on 29.02.2016.
 */
public class ModelConverter {

    @Nullable
    public static Link convertToLink(@Nullable NetworkLink networkLink) {
        if (networkLink == null) {
            return null;
        }
        Link link = new Link();
        link.setUrl(networkLink.getUrl());
        link.setType(networkLink.getType());
        link.setSuggestedLinkText(networkLink.getSuggestedLinkText());
        return link;
    }

    @Nullable
    public static Multimedia convertToMultimedia(@Nullable NetworkMultimedia networkMultimedia) {
        if (networkMultimedia == null) {
            return null;
        }
        Multimedia multimedia = new Multimedia();
        multimedia.setMovieReviewId(networkMultimedia.getMovieReviewId());
        multimedia.setResources(convertToResource(networkMultimedia.getResources()));
        return multimedia;
    }

    @Nullable
    public static Resource convertToResource(@Nullable NetworkResource networkResource) {
        if (networkResource == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setType(networkResource.getType());
        resource.setSrc(networkResource.getSrc());
        return resource;
    }

    @Nullable
    public static Review convertToReview(@Nullable NetworkReview networkReview) {
        if (networkReview == null) {
            return null;
        }
        Review review = new Review();
        review.setMovieId(networkReview.getNytMovieId());
        review.setDisplayTitle(networkReview.getDisplayTitle());
        review.setMpaaRating(networkReview.getMpaaRating());
        review.setByLine(networkReview.getByLine());
        review.setHeadLine(networkReview.getHeadLine());
        review.setPublicationDate(networkReview.getPublicationDate());
        review.setOpeningDate(networkReview.getOpeningDate());
        review.setDateUpdated(networkReview.getDateUpdated());
        review.setSummaryShort(networkReview.getSummaryShort());
        review.setSeoName(networkReview.getSeoName());
        review.setLink(convertToLink(networkReview.getNetworkLink()));
        review.setMultimedia(convertToMultimedia(networkReview.getNetworkMultimedia()));
        List<NetworkLink> relatedUrls = networkReview.getRelatedUrls();
        if (relatedUrls != null && !relatedUrls.isEmpty()) {
            RealmList<Link> realmRelatedUrls = new RealmList<>();
            for (NetworkLink relatedUrl : relatedUrls) {
                Link convertToLink = convertToLink(relatedUrl);
                if (convertToLink != null) {
                    realmRelatedUrls.add(convertToLink);
                }
            }
            review.setRelatedUrls(realmRelatedUrls);
        }
        return review;
    }

}
