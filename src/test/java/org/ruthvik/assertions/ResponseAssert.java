package org.ruthvik.assertions;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {
    private ResponseAssert(Response response, Class<?> selfType) {
        super(response, selfType);
    }

    public static ResponseAssert assertThat(Response response){
        return new ResponseAssert(response,ResponseAssert.class);
    }

    public ResponseAssert isGetRequestSuccessful(){
        Assertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
        return this;
    }
    public ResponseAssert isPostRequestSuccessful(){
        Assertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 201")
                .isEqualTo(201);
        return this;
    }
    public ResponseAssert isPutRequestSuccessful(){
        Assertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
        return this;
    }
    public ResponseAssert isDeleteRequestSuccessful(){
        Assertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
        return this;
    }
    public ResponseAssert hasContentTypeApplicationJson(){
        Assertions.assertThat(actual.getContentType())
                .withFailMessage(()->"Content type is not application/json but "+ actual.getContentType())
                .contains(ContentType.JSON.toString());
        return this;
    }


    public ResponseAssert isBadRequestAndHasMessage(String message){
        List<String> actualValue = actual.body().jsonPath().getList("message");
        Assertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not "+ 400)
                .isEqualTo(400);
        Assertions.assertThat(actualValue).contains(message);
        return this;
    }
}
