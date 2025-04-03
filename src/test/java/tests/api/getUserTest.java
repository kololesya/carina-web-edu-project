package tests.api;

import api.get.GetSingleUserMethod;
import api.get.GetUserNotFoundMethod;
import api.get.GetUsersMethods;
import com.zebrunner.carina.api.apitools.validation.JsonCompareKeywords;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

public class getUserTest implements IAbstractTest {
    @Test()
    @MethodOwner(owner = "Olesya")
    public void testGetUsers() {
        GetUsersMethods getUsersMethods = new GetUsersMethods();
        getUsersMethods.callAPIExpectSuccess();
        getUsersMethods.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getUsersMethods.validateResponseAgainstSchema("api/_get/all_users_rs.schema");
    }

    @Test
    @MethodOwner(owner= "Olesya")
    public void testGetUser(){
        GetSingleUserMethod getSingleUserMethod = new GetSingleUserMethod();
        getSingleUserMethod.callAPIExpectSuccess();
        getSingleUserMethod.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getSingleUserMethod.validateResponseAgainstSchema("api/_get/single_user_rs.json");
    }

    @Test
    @MethodOwner(owner= "Olesya")
    public void testGetUserNotFound(){
        GetUserNotFoundMethod getUserNotFoundMethod = new GetUserNotFoundMethod();
        getUserNotFoundMethod.callAPIExpectSuccess();
        getUserNotFoundMethod.validateResponse(JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        getUserNotFoundMethod.validateResponseAgainstSchema("api/_get/user_not_found_rs.json");
    }
}
