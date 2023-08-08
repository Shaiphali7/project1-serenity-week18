package com.bestbuy.storeinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class StoreCURDTestWithSteps extends TestBase {
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
    int id = 1963;
    String serviceName = "Apple Shop";
    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        storeSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(201);
    }

    @Test
    public void test02() {
        int sId = storeSteps.findByStore(state);
        System.out.println("value of ID" + sId);
        Assert.assertEquals(sId, 54);
    }

    @Test
    public void findStroresWithServiceName() {
        int sId = storeSteps.findByServiceStore(serviceName);
        System.out.println("value of ID" + sId);
        Assert.assertEquals(sId, 756);
    }

    @Test
    public void updateID() {
        storeSteps.UpdateStore("New store", type, address, address2, city, state, zip, lat, lng, hours, id);
    }

    @Test
    public void deleteById() {

        storeSteps.deleteStudent(id).statusCode(204);
        storeSteps.getStoreById(id).statusCode(404);

    }

}
