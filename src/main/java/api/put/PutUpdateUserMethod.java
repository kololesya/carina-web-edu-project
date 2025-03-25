package api.put;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}api/users/2", methodType = HttpMethodType.PUT)
@RequestTemplatePath(path = "api/_put/update_user_rq.json")
@ResponseTemplatePath(path = "api/_put/update_user_rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PutUpdateUserMethod extends AbstractApiMethodV2 {
    public PutUpdateUserMethod() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
