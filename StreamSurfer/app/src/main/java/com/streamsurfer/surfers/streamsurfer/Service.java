package com.streamsurfer.surfers.streamsurfer;

/**
 * Created by Jack on 3/1/2017.
 */

public class Service {
    private String name;
    private String baseUrl;
    private String icon;

    public Service(String name, String baseUrl, String icon) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getIcon() {
        return icon;
    }
}
