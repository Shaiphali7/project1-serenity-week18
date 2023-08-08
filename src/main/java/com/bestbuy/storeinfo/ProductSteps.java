package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;


import static net.serenitybdd.rest.RestRequests.given;

public class ProductSteps {
    @Step("Creating product with name :{0},type: {1}, upc : {2}, price : {3}, description : {4} and model: {5}")
    public ValidatableResponse createProduct(String name, String type, String upc, Double price, String description, String model)
    {
        ProductPojo productPojo=new ProductPojo();
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);

        return SerenityRest.given()
            .contentType(ContentType.JSON)
            .when()
            .body(productPojo)
            .post()
            .then();
    }
    @Step("Deleting store information with storeId: {0}")
    public ValidatableResponse deleteStudent(int id) {
        return    given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_Product_BY_ID)
                .then();
    }

    @Step("Getting store information with storeId : {0}")
    public ValidatableResponse getStoreById(int id) {
        return   given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.DELETE_Product_BY_ID)
                .then();
    }
    @Step("Getting all the product")
    public void GetAllProduct()
    {
        SerenityRest.given()
                .when()
                .get()
                .then().log().all().statusCode(200);
    }
    @Step("Creating product with name :{0},type: {1}, upc : {2}, price : {3}, description : {4}, model: {5} and id : {6}")
    public ValidatableResponse updateById(String name, String type, String upc, Double price, String description, String model,int id)
    {
        description="This is a placeholder request for creating";
        ProductPojo productPojo=new ProductPojo();
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);

      return   SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_Product_BY_id)
                .then();
    }
}
