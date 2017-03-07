package com.streamsurfer.surfers.streamsurfer;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (object instanceof Service) {
            Service check = (Service) object;
            if (check.getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
}
