package org.ruthvik.modules;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.ruthvik.assertions.ResponseAssert;
import org.ruthvik.assertions.UserAssert;
import org.ruthvik.controller.AuthAPI;
import org.ruthvik.controller.UserAPI;
import org.ruthvik.pojo.auth.AuthRequest;
import org.ruthvik.pojo.user.UserRequest;
import org.ruthvik.pojo.user.UserResponse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserAPITests {
    private static long userId;
    private static UserResponse userResponse;

    @Test
    @Order(1)
    @Tag("positive")
    void postUserWillNeededFields() {
        UserRequest user = UserRequest.builder()
                .setEmail("TonyStark@gmail.com")
                .setName("TonyStark")
                .setPassword("Tony123")
                .setAvatar("https://example.com/avatar.jpg")
                .build();

        Response response = new UserAPI().createUser(user);

        userId = response.getBody().jsonPath().getLong("id");
        userResponse = response.as(UserResponse.class);

        ResponseAssert.assertThat(response)
                .hasContentTypeApplicationJson()
                .isPostRequestSuccessful();
        UserAssert.assertThat(response)
                .validateSingleUserSchema();
    }

    @Test
    @Order(2)
    @Tag("positive")
    void getUserWithExistingIdAndCheckId() {

        Response response = new UserAPI().getUser(userId);

        Assertions.assertEquals(response.as(UserResponse.class), userResponse);

        ResponseAssert.assertThat(response)
                .isGetRequestSuccessful()
                .hasContentTypeApplicationJson();

        UserAssert.assertThat(response)
                .validateSingleUserSchema();
    }

    @Test
    @Order(3)
    @Tag("positive")
    void updateUserWithValidId() {
        final String updateName = "Tony Start A.K.A IronMan";
        UserRequest user = UserRequest.builder()
                .setName(updateName)
                .build();

        Response response = new UserAPI().updateUser(userId, user);

        String newName = response.getBody().jsonPath().getString("name");

        Assertions.assertEquals(newName, updateName);

        ResponseAssert.assertThat(response)
                .hasContentTypeApplicationJson()
                .isPutRequestSuccessful();

        UserAssert.assertThat(response)
                .validateSingleUserSchema();

        userResponse = response.as(UserResponse.class);
    }

    @Test
    @Tag("positive")
    @Tag("fail")
    @Order(4)
    void loginWithValidCredentialsAndVerifyUserProfile() {

        UserResponse updatedUser = userResponse;
        AuthRequest request = AuthRequest.builder().setEmail(updatedUser.getEmail()).setPassword(updatedUser.getPassword()).build();

        Response verifyResponse = new AuthAPI().viewProfile(request);
        verifyResponse.prettyPrint();

        Assertions.assertEquals(verifyResponse.as(UserResponse.class),updatedUser);
    }

    @Test
    @Order(5)
    @Tag("positive")
    void deleteUserWithValidId() {
        Response response = new UserAPI().deleteUser(userId);
        ResponseAssert.assertThat(response)
                .isDeleteRequestSuccessful();
    }

    @Test
    @Tag("positive")
    void getUsersWithLimitReturnsCorrectNumberOfUsers() {
        int expectedNumberOfUsers = 2;

        Response response = new UserAPI().getUsers(expectedNumberOfUsers);
        response.prettyPrint();

        ResponseAssert.assertThat(response)
                .hasContentTypeApplicationJson()
                .isGetRequestSuccessful();

        UserAssert.assertThat(response)
                .hasNumberOfUsers(expectedNumberOfUsers)
                .validateListOfUsersSchema();
    }

    @Test
    @Tag("negative")
    void postUserWithoutEmail(){
        UserRequest user = UserRequest.builder()
                .setName("TonyStark")
                .setPassword("Tony123")
                .setAvatar("https://example.com/avatar.jpg")
                .build();

        Response response = new UserAPI().createUser(user);
        ResponseAssert.assertThat(response)
                .hasContentTypeApplicationJson()
                .isBadRequestAndHasMessage("email should not be empty");
    }
    @Test
    @Tag("negative")
    void postUserWithInvalidEmailFormat(){
        UserRequest user = UserRequest.builder()
                .setName("TonyStark")
                .setEmail("ajhsdfjsdbf")
                .setPassword("Tony123")
                .setAvatar("https://example.com/avatar.jpg")
                .build();

        Response response = new UserAPI().createUser(user);
        ResponseAssert.assertThat(response)
                .hasContentTypeApplicationJson()
                .isBadRequestAndHasMessage("email must be an email");
    }
}