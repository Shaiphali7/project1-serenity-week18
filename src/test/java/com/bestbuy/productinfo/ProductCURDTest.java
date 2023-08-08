package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase1;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;

import static net.serenitybdd.rest.RestRequests.given;

public class ProductCURDTest extends TestBase1 {
    static String name="New Product"+ TestUtils.getRandomValue();
    static   String  type="Hard Good";
    static String upc="123456";
    private double price=99.99;
    static int id=9999680;
    private  String description="This is a placeholder request for creating a new product.";
    private  String model="NP12345";
    @Title("Getting all the stores")
    @Test
    public void test002()
    {
        SerenityRest.given()
                .when()
                .get()
                .then().log().all().statusCode(200);
    }

    @Title("This will create a new product")
    @Test
    public void test001()
    {
        ProductPojo productPojo=new ProductPojo();
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);

        SerenityRest.given() .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .body(productPojo)
                .post()
                .then().log().all()
                .statusCode(201);
    }
    @Title("Update the product Information and verify the updated information")
    @Test
    public void updateID(){
        description="This is a placeholder request for creating";
        ProductPojo productPojo=new ProductPojo();
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);

        SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_Product_BY_id)
                .then().log().all().statusCode(200);

    }
    @Test
    public void findByStore()
    {
        Response response = given().queryParam("$select[]", "name").queryParam("$select[]","description")
                .basePath("/products")
                .header("Content-Type", "application/json")
                .when()
                .get();
        response.then().statusCode(200);
        response.prettyPrint();

    }
    @Test
    public void findByPrice()
    {
        Response response = given().queryParam("$sort[price]", "-1")
                .basePath("/products")
                .header("Content-Type", "application/json")
                .when()
                .get();
        response.then().statusCode(200);
        response.prettyPrint();
    }
    @Test
    public void deleteId(){
        given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_Product_BY_ID)
                .then()
                .statusCode(200);

        given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.DELETE_Product_BY_ID)
                .then().statusCode(404);
    }
}



