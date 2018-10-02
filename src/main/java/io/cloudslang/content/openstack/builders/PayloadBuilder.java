package io.cloudslang.content.openstack.builders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.identity.requests.TokenRequest;

import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.client.methods.HttpPost.METHOD_NAME;

public class PayloadBuilder {
    private static final Gson GSON = new GsonBuilder().create();

    private PayloadBuilder() {
    }

    public static String buildPayload(InputsWrapper wrapper) {
        if (METHOD_NAME.equals(wrapper.getHttpClientInputs().getMethod())) {
            switch (wrapper.getCommonInputsBuilder().getAction()) {
                case PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION:
                    return GSON.toJson(new TokenRequest(wrapper.getIdentityInputsBuilder().getAuth()), TokenRequest.class);
                default:
                    return EMPTY;
            }
        }

        return EMPTY;
    }
}
