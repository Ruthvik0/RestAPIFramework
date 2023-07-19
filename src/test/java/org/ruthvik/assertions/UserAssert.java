package org.ruthvik.assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.ruthvik.pojo.user.UserResponse;

import java.io.File;
import java.util.List;

public class UserAssert extends AbstractAssert<UserAssert, Response> {
    private UserAssert(Response response, Class<?> selfType) {
        super(response, selfType);
    }

    public static UserAssert assertThat(Response response) {
        return new UserAssert(response, UserAssert.class);
    }

    public UserAssert hasNumberOfUsers(int expectedNumberOfUsers) {
        ObjectMapper mapper = new ObjectMapper();
        List<UserResponse> userResponses;
        try {
            userResponses = mapper.readValue(actual.getBody().asString(), new TypeReference<List<UserResponse>>() {});
        } catch (JsonProcessingException e) {
            throw new AssertionError("Error deserializing the response body to List<UserResponse>.", e);
        }
        Assertions.assertThat(userResponses).hasSize(expectedNumberOfUsers);
        return this;
    }

    public UserAssert validateListOfUsersSchema(){
        File file = new File(System.getProperty("user.dir")+"/src/test/resources/schemas/ListUserResponseSchema.json");
        Assertions.assertThat(actual.then().body(JsonSchemaValidator.matchesJsonSchema(file)))
                .withFailMessage("Schema is not matching please check [ListUserResponseSchema.json] file")
                .getWritableAssertionInfo();
        return this;
    }
    public UserAssert validateSingleUserSchema(){
        File file = new File(System.getProperty("user.dir")+"/src/test/resources/schemas/SingleUserResponseSchema.json");
        Assertions.assertThat(actual.then().body(JsonSchemaValidator.matchesJsonSchema(file)))
                .withFailMessage("Schema is not matching please check [SingleUserResponseSchema.json] file")
                .getWritableAssertionInfo();
        return this;
    }

    public UserAssert validateProperty(String key, Object expectedValue){
        Object actualValue = actual.body().jsonPath().get(key);
        Assertions.assertThat(actualValue)
                .withFailMessage(()->"Actual value [%s] is not equal to expected value [%s]".formatted(actualValue,expectedValue))
                .isEqualTo(expectedValue);
        return this;
    }
}
