package api.get;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}api/users?page=2", methodType = HttpMethodType.GET)
@ResponseTemplatePath(path = "api/_get/all_users_rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetUsersMethods extends AbstractApiMethodV2 {

    public GetUsersMethods() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
