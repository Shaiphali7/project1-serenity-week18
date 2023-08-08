package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.RestRequests.given;

@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {
    static String name = "shoe store" + TestUtils.getRandomValue();

    static String type = "BigBox" + TestUtils.getRandomValue();
    static String address = "123 Fake St";
    static String address2 = "St petrs";
    static String city = "Springfield";
    static String state = "MN";
    static String zip = "55123";
    static Double lat = 44.969658;
    static Double lng = -93.449539;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    int id=8932;
    String serviceName="Apple Shop";
    @Test
    public void test001() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setHours(hours);
        storePojo.setLng(lng);
        storePojo.setLat(lat);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setType(type);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post()
                .then().log().all().statusCode(201);
    }
    @Title("Verify store is added to the application")
    @Test
    public void test002()
    {
//     HashMap<String,Object> storeMap=SerenityRest.given()
//             .when()
//             .get()
    }
    @Title("Update the store information")
    @Test
    public void test003()
    {
        type =type +"_updated";
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setHours(hours);
        storePojo.setLng(lng);
        storePojo.setLat(lat);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setType(type);
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .queryParams("name",name)
                .body(storePojo)
                .when()
                .put()
                .then().log().all().statusCode(200);
    }
    @Title("Getting all data where state=MN")
    @Test
    public void test004()
    {
SerenityRest.given()
        .queryParams("state","MN")
        .when()
        .get()
        .then().log().all().statusCode(200);

    }
    @Test
    public void test005()
    {
        SerenityRest.given()
                .queryParams("service.name","Apple Shop")
                .when()
                .get()
                .then().log().all().statusCode(200);
    }
    @Test
    public void findStores(){
        int sId=given()
                .queryParam("state",state)
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path("total");
        System.out.println("value of ID"+sId);
        Assert.assertEquals(sId,56);
    }
    @Test
    public void findStroresWithServiceName(){
        int sId=given()
                .queryParam("service.name",serviceName)
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path("total");
        System.out.println("value of ID"+sId);
        Assert.assertEquals(sId,756);
    }
    @Title("Update the store Information and verify the updated information")
    @Test
    public void updateID(){
        StorePojo storePojo=new StorePojo();
        storePojo.setName("New Store");
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours("Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8");
        SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(storePojo)
                .when()
                .patch(EndPoints.UPDATE_STORE_BY_id)
                .then().log().all().statusCode(200);

    }
    @Test
    public void deleteId(){
        given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then()
                .statusCode(200);

        given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.DELETE_STORE_BY_ID)
                .then().statusCode(404);
    }

}