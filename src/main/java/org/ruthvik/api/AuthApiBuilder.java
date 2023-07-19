package org.ruthvik.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.ruthvik.controller.AuthAPI;
import org.ruthvik.enums.ApiMethod;
import org.ruthvik.pojo.auth.AuthRequest;
import org.ruthvik.pojo.auth.AuthResponse;

public final class AuthApiBuilder extends ApiBuilder {
    private final AuthResponse authResponse;

    public AuthApiBuilder(AuthRequest request) {
        this.authResponse = new AuthAPI().login(request);
    }

    @Override
    public Response fetchResponse(ApiMethod method, String endpoint) {
        RequestSpecification requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + authResponse.getAccessToken())
                .spec(this.getRequestSpecBuilder().build());

        return switch (method) {
            case GET -> requestSpec.get(endpoint).thenReturn();
            case POST -> requestSpec.post(endpoint).thenReturn();
            case PUT -> requestSpec.put(endpoint).thenReturn();
            case DELETE -> requestSpec.delete(endpoint).thenReturn();
            case PATCH -> requestSpec.patch(endpoint).thenReturn();
        };
    }
}
