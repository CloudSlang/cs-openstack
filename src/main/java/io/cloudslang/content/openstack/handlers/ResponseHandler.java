package io.cloudslang.content.openstack.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.compute.responses.api.ListAllMajorVersionsResponse;
import io.cloudslang.content.openstack.compute.responses.servers.ListServersResponse;
import io.cloudslang.content.openstack.identity.responses.AuthenticationResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.httpclient.services.HttpClientService.RESPONSE_HEADERS;
import static io.cloudslang.content.openstack.entities.Constants.Headers.TOKEN;
import static io.cloudslang.content.openstack.entities.Constants.Headers.X_OPENSTACK_REQUEST_ID;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.entities.Constants.Responses.REQUEST_TRACKING_ID;
import static io.cloudslang.content.openstack.entities.Constants.Values.THRESHOLD_VERSION_FOR_REQUEST_UUID_PRESENCE;
import static io.cloudslang.content.openstack.identity.entities.Constants.Headers.X_SUBJECT_TOKEN;
import static io.cloudslang.content.openstack.validators.Validators.isInputGreaterOrEqualThanThreshold;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.join;

public class ResponseHandler {
    private static final Gson GSON = new GsonBuilder().create();

    private ResponseHandler() {
    }

    public static <T> String handleResponse(String input, Class<T> classOfT) {
        return Optional
                .ofNullable(input)
                .filter(StringUtils::isNotEmpty)
                .map(s -> extractSpecificResponseString(input, classOfT))
                .orElse(EMPTY);
    }

    public static void gatherAdditionalResponseInfo(String baseVersion, Map<String, String> response) {
        String token = getHeaderValue(response.get(RESPONSE_HEADERS), X_SUBJECT_TOKEN);

        Optional
                .ofNullable(token)
                .filter(StringUtils::isNotEmpty)
                .ifPresent(add -> response.put(TOKEN, token));

        Optional
                .of(isInputGreaterOrEqualThanThreshold(baseVersion, THRESHOLD_VERSION_FOR_REQUEST_UUID_PRESENCE))
                .ifPresent(add -> response.put(REQUEST_TRACKING_ID, getHeaderValue(response.get(RESPONSE_HEADERS), X_OPENSTACK_REQUEST_ID)));
    }

    static String getHeaderValue(String input, String headerName) {
        return Optional
                .ofNullable(headerName)
                .filter(StringUtils::isNotEmpty)
                .map(result -> Pattern.compile("\\n")
                        .splitAsStream(input)
                        .filter(Objects::nonNull)
                        .collect(toList())
                        .stream()
                        .filter(f -> f.startsWith(headerName))
                        .map(s -> s.substring(headerName.length() + 2))
                        .findFirst()
                        .orElse(EMPTY))
                .orElse(EMPTY);
    }

    private static <T> String extractSpecificResponseString(String input, Class<T> classOfT) {
        if (ListAllMajorVersionsResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            return handleApiResponse(GSON.fromJson(input, ListAllMajorVersionsResponse.class));
        } else if (AuthenticationResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            return handleAuthenticationResponse(GSON.fromJson(input, AuthenticationResponse.class));
        } else if (ListServersResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            return handleListServersResponse(GSON.fromJson(input, ListServersResponse.class));
        }

        return EMPTY;
    }

    private static String handleApiResponse(ListAllMajorVersionsResponse listAllMajorVersionsResponse) {
        StringBuilder sb = new StringBuilder();

        Optional
                .ofNullable(listAllMajorVersionsResponse)
                .ifPresent(append -> listAllMajorVersionsResponse.getVersions().stream()
                        .filter(Objects::nonNull)
                        .forEach(version -> sb.append(version.getId()).append(join(COMMA_DELIMITER, BLANK_SPACE))));

        return Optional
                .of(sb.toString())
                .filter(StringUtils::isNotEmpty)
                .map(s -> sb.deleteCharAt(sb.length() - 2).toString().trim())
                .orElse(EMPTY);
    }

    private static String handleListServersResponse(ListServersResponse listServersResponse) {
        StringBuilder sb = new StringBuilder();

        Optional
                .ofNullable(listServersResponse)
                .ifPresent(append -> listServersResponse.getServers().stream()
                        .filter(Objects::nonNull)
                        .forEach(server -> sb.append(server.getName()).append(join(COMMA_DELIMITER, BLANK_SPACE))));

        return Optional
                .of(sb.toString())
                .filter(StringUtils::isNotEmpty)
                .map(s -> sb.deleteCharAt(sb.length() - 2).toString().trim())
                .orElse(EMPTY);
    }

    private static String handleAuthenticationResponse(AuthenticationResponse authenticationResponse) {
        return Optional
                .ofNullable(authenticationResponse.getToken().getExpiresAt())
                .orElse(EMPTY);
    }
}
