package org.ruthvik.controller;

import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.ruthvik.api.ApiBuilder;
import org.ruthvik.config.ConfigReader;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.user.UserRequest;

public final class UserAPI {
    private final ConfigReader config = ConfigFactory.create(ConfigReader.class);
    private final String BASE_URL = config.baseUrl();

    public Response getUsers(int limit) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .addQueryParam("limit", limit)
                .fetchResponse(ApiMethod.GET, config.userEndpoint());
    }

    public Response getUser(long id) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .fetchResponse(ApiMethod.GET, config.userEndpoint() + "/" + id);
    }

    public Response createUser(UserRequest user) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .body(user)
                .fetchResponse(ApiMethod.POST, config.userEndpoint());
    }

    public Response updateUser(long id, UserRequest user) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .body(user)
                .fetchResponse(ApiMethod.PUT, config.userEndpoint() + "/" + id);
    }

    public Response deleteUser(long id) {
        return new ApiBuilder()
                .baseUri(BASE_URL)
                .fetchResponse(ApiMethod.DELETE, config.userEndpoint() + "/" + id);
    }
}
