package ua.marinovskiy.moviereviewsny.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 26.02.2016.
 */
public class NetworkMultimedia {

    @SerializedName("resource")
    private List<NetworkResource> resources;

    public List<NetworkResource> getResources() {
        return resources;
    }

    public void setResources(List<NetworkResource> resources) {
        this.resources = resources;
    }
}
