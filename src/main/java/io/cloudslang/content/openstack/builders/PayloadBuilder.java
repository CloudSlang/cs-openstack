package io.cloudslang.content.openstack.builders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.identity.requests.TokenRequest;

import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.vavr.API.*;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.client.methods.HttpPost.METHOD_NAME;

public class PayloadBuilder {
    private static final Gson GSON = new GsonBuilder().create();

    private PayloadBuilder() {
    }

    public static String buildPayload(InputsWrapper wrapper) {
        return of(wrapper)
                .filter(f -> METHOD_NAME.equals(wrapper.getHttpClientInputs().getMethod()))
                .map(payload -> {
                    final String action = wrapper.getCommonInputsBuilder().getAction();

                    return Match(action)
                            .of(
                                    Case($(qcd -> PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION.equalsIgnoreCase(action)),
                                            () -> GSON.toJson(new TokenRequest(wrapper.getIdentityInputsBuilder().getAuth()), TokenRequest.class)),
                                    Case($(), () -> EMPTY)
                            );
                })
                .orElse(EMPTY);
    }
}
