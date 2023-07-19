package org.ruthvik.controller;

import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.ruthvik.api.ApiBuilder;
import org.ruthvik.api.AuthApiBuilder;
import org.ruthvik.config.ConfigReader;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.auth.AuthRequest;
import org.ruthvik.pojo.auth.AuthResponse;

public class AuthAPI {
    private final ConfigReader config = ConfigFactory.create(ConfigReader.class);
    private final String BASE_URL = config.baseUrl();

    public AuthResponse login(AuthRequest request) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .body(request)
                .fetchResponse(ApiMethod.POST, config.authLoginEndpoint()).as(AuthResponse.class);
    }

    public Response viewProfile(AuthRequest auth) {
        return new AuthApiBuilder(auth)
                .baseUri(BASE_URL)
                .fetchResponse(ApiMethod.GET, config.authViewProfile());
    }
}
