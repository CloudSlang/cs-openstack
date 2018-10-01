package io.cloudslang.content.openstack.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.compute.responses.api.ListAllMajorVersionsResponse;
import io.cloudslang.content.openstack.identity.responses.AuthenticationResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.BLANK_SPACE;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

public class ResponseHandler {
    private static final Gson GSON = new GsonBuilder().create();

    private ResponseHandler() {
    }

    public static <T> String handleResponse(String input, Class<T> classOfT) {
        if (isNotBlank(input) && ListAllMajorVersionsResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            //noinspection unchecked
            return handleApiResponse(GSON.fromJson(input, ListAllMajorVersionsResponse.class));
        } else if (isNotBlank(input) && AuthenticationResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            return handleAuthenticationResponse(GSON.fromJson(input, AuthenticationResponse.class));
        }

        return EMPTY;
    }

    public static String getHeaderValue(String input, String headerName) {
        return isNotBlank(headerName) ?
                Pattern.compile("\\n")
                        .splitAsStream(input)
                        .filter(Objects::nonNull)
                        .collect(toList())
                        .stream()
                        .filter(f -> f.startsWith(headerName))
                        .map(s -> s.substring(headerName.length() + 2))
                        .findFirst()
                        .orElse(EMPTY) : EMPTY;
    }

    private static String handleApiResponse(ListAllMajorVersionsResponse listAllMajorVersionsResponse) {
        StringBuilder sb = new StringBuilder();

        if (listAllMajorVersionsResponse != null) {
            listAllMajorVersionsResponse.getVersions().stream()
                    .filter(Objects::nonNull)
                    .forEach(version -> sb.append(version.getId()).append(join(COMMA_DELIMITER, BLANK_SPACE)));
        }

        return isNotBlank(sb.toString()) ? sb.deleteCharAt(sb.length() - 2).toString().trim() : EMPTY;
    }

    private static String handleAuthenticationResponse(AuthenticationResponse authenticationResponse) {
        return Optional
                .ofNullable(authenticationResponse.getToken().getExpiresAt())
                .orElse(EMPTY);
    }
}
