package tests.api;

import api.put.PutUpdateUserMethod;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class updateUserTest {
    @Test
    @MethodOwner(owner = "Olesya")
    public void testUpdateUser() {
        PutUpdateUserMethod putUpdateUserMethod = new PutUpdateUserMethod();

        Response response = putUpdateUserMethod.callAPIExpectSuccess();

        String responseBody = response.asString();

        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        String updatedAt = json.get("updatedAt").getAsString();

        String actualDate = updatedAt.substring(0, 10);
        String expectedDate = LocalDate.now().toString();

        Assert.assertEquals(actualDate, expectedDate, "Check if the date of updating is actual");
    }
}
