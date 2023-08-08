package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.rest.RestRequests.given;

public class StoreSteps {
    @Step("Creating student with name : {0},type : {1}, address : {2}, address2 : {3} city : {4}, state : {5}, zip : {7},lat :{8},lng : {9},hours :{10}")
    public ValidatableResponse createStore(String name, String type ,String address, String address2 ,String city,String state ,String zip,double lat,double lng,String hours){
        StorePojo storepojo= new StorePojo();
        storepojo.setName(name);
        storepojo.setType(type);
        storepojo.setAddress(address);
        storepojo.setAddress2(address2);
        storepojo.setCity(city);
        storepojo.setState(state);
        storepojo.setZip(zip);
        storepojo.setLat(lat);
        storepojo.setLng(lng);
        storepojo.setHours(hours);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(storepojo)
                .post()
                .then();
    }
    @Step("Getting store information by store name: {0}")
    public int findByStore(String state)
    {
        return given()
                .queryParam("state",state)
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path("total");
    }
    @Step("Getting store information by store ServiceName: {0}")
    public int findByServiceStore(String serviceName)
    {
        return given()
                .queryParam("service.name",serviceName)
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path("total");
    }
    @Step("Update the store Information name : {0},type : {1}, address : {2}, address2 : {3} city : {4}, state : {5}, zip : {7},lat :{8},lng : {9},hours :{10}")
    public void UpdateStore(String name, String type ,String address, String address2 ,String city,String state ,String zip,double lat,double lng,String hours,int id)
    {
        StorePojo storePojo=new StorePojo();
        storePojo.setName(name);
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
    @Step("Deleting store information with storeId: {0}")
    public ValidatableResponse deleteStudent(int id) {
     return    given()
             .pathParam("id", id)
             .when()
             .delete(EndPoints.DELETE_STORE_BY_ID)
             .then();
    }

    @Step("Getting store information with storeId : {0}")
    public ValidatableResponse getStoreById(int id) {
      return   given()
              .pathParam("id", id)
              .when()
              .get(EndPoints.DELETE_STORE_BY_ID)
              .then();
    }
    }

