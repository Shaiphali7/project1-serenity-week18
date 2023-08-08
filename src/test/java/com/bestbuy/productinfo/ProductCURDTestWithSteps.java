package com.bestbuy.productinfo;

import com.bestbuy.storeinfo.ProductSteps;
import com.bestbuy.testbase.TestBase1;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.RestRequests.given;
@RunWith(SerenityRunner.class)
public class ProductCURDTestWithSteps extends TestBase1 {
    static String name="New Product"+ TestUtils.getRandomValue();
    static   String  type="Hard Good";
    static String upc="123456";
    private double price=99.99;
    static int id=9999680;
    private  String description="This is a placeholder request for creating a new product.";
    private  String model="NP12345";

    @Steps
    ProductSteps productSteps;
    @Title("Getting all the stores")
    @Test
    public void test002()
    {
        productSteps.GetAllProduct();
    }

    @Title("This will create a new product")
    @Test
    public void test001()
    {
       productSteps.createProduct(name,type,upc,price,description,model).statusCode(201);
    }
    @Title("Update the product Information and verify the updated information")
    @Test
    public void updateID(){
       productSteps.updateById(name,type,upc,price,description,model,id).statusCode(200);

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
        productSteps.deleteStudent(id).statusCode(204);
        productSteps.getStoreById(id).statusCode(404);
    }
}
