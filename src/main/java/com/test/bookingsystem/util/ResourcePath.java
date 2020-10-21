package com.test.bookingsystem.util;

import org.springframework.http.HttpMethod;

public class ResourcePath {
    public static final String V1_TICKETS_PATH = "/v1/tickets";
    public static final String V1_MOVIE_SHOWS_PATH = "/v1/movie-shows";

    public static final String V1_THEATRES_PATH = "/v1/theatres";
    public static final String V1_SCREENS_PATH = "/v1/theatres/{theatreId}/screens";
    public static final String V1_SHOWS_PATH = "/v1/shows";

    public static final String USERS_PATH = "/users";

    public static Boolean isUserPermittedAction(String path, String httpMethod) {
        return httpMethod.equalsIgnoreCase(HttpMethod.GET.name()) || path.startsWith(V1_TICKETS_PATH);
    }

}
