package org.ruthvik.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.ruthvik.enums.ApiMethod;

import java.util.Map;

public class ApiBuilder {
    private final RequestSpecBuilder requestSpecBuilder;

    public ApiBuilder() {
        this.requestSpecBuilder = new RequestSpecBuilder();
    }

    protected RequestSpecBuilder getRequestSpecBuilder() {
        return this.requestSpecBuilder;
    }

    public ApiBuilder baseUri(String baseUri) {
        requestSpecBuilder.setBaseUri(baseUri);
        return this;
    }

    public ApiBuilder headers(Map<String, String> headers) {
        requestSpecBuilder.addHeaders(headers);
        return this;
    }

    public ApiBuilder addQueryParam(String query, Object value) {
        requestSpecBuilder.addQueryParam(query, value);
        return this;
    }

    public ApiBuilder body(Object requestBody) {
        requestSpecBuilder.setBody(requestBody);
        return this;
    }

    public Response fetchResponse(ApiMethod method, String endpoint) {
        RequestSpecification requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .spec(this.requestSpecBuilder.build());

        return switch (method) {
            case GET -> requestSpec.get(endpoint).thenReturn();
            case POST -> requestSpec.post(endpoint).thenReturn();
            case PUT -> requestSpec.put(endpoint).thenReturn();
            case DELETE -> requestSpec.delete(endpoint).thenReturn();
            case PATCH -> requestSpec.patch(endpoint).thenReturn();
        };
    }
}
