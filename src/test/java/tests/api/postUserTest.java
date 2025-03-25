package tests.api;

import api.post.PostRegisterInvalidUserMethod;
import api.post.PostUserMethod;
import com.google.gson.JsonParser;

import com.google.gson.JsonObject;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.api.apitools.validation.JsonCompareKeywords;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
public class postUserTest {
    @Test
    @MethodOwner(owner = "Olesya")
    public void testCreateUser() {
        PostUserMethod postUserMethod = new PostUserMethod();
        postUserMethod.setProperties("api/_post/user.properties");

        Response response = postUserMethod.callAPIExpectSuccess();
        String responseBody = response.asString();

        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

        Assert.assertEquals(json.get("name").getAsString(), "morpheus");
        Assert.assertEquals(json.get("job").getAsString(), "leader");
        Assert.assertTrue(json.has("id"));
        Assert.assertFalse(json.get("id").getAsString().isEmpty());

        Assert.assertTrue(json.has("createdAt"));
        Assert.assertFalse(json.get("createdAt").getAsString().isEmpty());
    }

    @Test
    @MethodOwner(owner = "Olesya")
    public void testRegisterInvalidUser() {
        PostRegisterInvalidUserMethod postRegister = new PostRegisterInvalidUserMethod();

        Response response = postRegister.callAPI();

        Assert.assertEquals(response.getStatusCode(), 400);

        String responseBody = response.asString();
        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        Assert.assertEquals(json.get("error").getAsString(), "Missing password");
    }

}
