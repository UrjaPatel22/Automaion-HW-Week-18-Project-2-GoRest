package com.gorest.userinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTestWithSteps extends TestBase {

    static int userId;
    static String name = "testprime" + TestUtils.getRandomValue();
    static String gender = "male";
    static String email =  TestUtils.getRandomValue() + "testprime@gmail.com" ;

    static String status = "active";



    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public  void test001(){
        ValidatableResponse response=userSteps.createNewUsers(name,gender,email,status);
        response.log().all().statusCode(201);
        userId = response.log().all().extract().path("id");
        System.out.println("Id is" + userId);
    }

    @Title("Verify If the Product was Added to the application")
    @Test
    public void test002(){
        HashMap<String,Object> UserMap= userSteps.getUserInfoById(userId);
        Assert.assertThat(UserMap, hasValue(userId));

    }


    @Title("Update the User information and Verify the Updated information")
    @Test
    public  void test003(){
        name=name + "_Updated";
        userSteps.updateUser(userId,name,gender,email,status)
                .statusCode(200);
        HashMap<String,Object> productMap= userSteps.getUserInfoById(userId);
        Assert.assertThat(productMap, hasValue(name));
    }
    @Title("Delete the User and verify if the User is deleted")
    @Test
    public void test004(){
        userSteps.deleteUser(userId)
                .statusCode(204);
        userSteps.getAllUserInfo().statusCode(200);


    }

}
