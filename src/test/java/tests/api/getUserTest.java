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





//    @Test()
//    @MethodOwner(owner = "qpsdemo")
//    public void testCreateUserMissingSomeFields() throws Exception {
//        PostUserMethod api = new PostUserMethod();//        api.setProperties("api/users/user.properties");
//        api.getProperties().remove("name");
//        api.getProperties().remove("username");
//        api.callAPIExpectSuccess();
//        api.validateResponse();
//    }
//

//
//    @Test()
//    @MethodOwner(owner = "qpsdemo")
//    @TestPriority(Priority.P1)
//    public void testDeleteUsers() {
//        DeleteUserMethod deleteUserMethod = new DeleteUserMethod();
//        deleteUserMethod.setProperties("api/users/user.properties");
//        deleteUserMethod.callAPIExpectSuccess();
//        deleteUserMethod.validateResponse();
//    }

}
