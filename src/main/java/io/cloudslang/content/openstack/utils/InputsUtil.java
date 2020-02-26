package io.cloudslang.content.openstack.utils;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.compute.entities.ComputeApi;
import io.cloudslang.content.openstack.compute.entities.servers.AutoDiskConfig;
import io.cloudslang.content.openstack.compute.entities.servers.LockedBy;
import io.cloudslang.content.openstack.compute.entities.servers.PowerState;
import io.cloudslang.content.openstack.compute.entities.servers.ServersApi;
import io.cloudslang.content.openstack.compute.entities.servers.SortKey;
import io.cloudslang.content.openstack.compute.entities.servers.Status;
import io.cloudslang.content.openstack.compute.entities.servers.VmState;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.entities.AuthenticationMethod;
import io.cloudslang.content.openstack.identity.entities.IdentityApi;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.StringJoiner;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.openstack.builders.PayloadBuilder.buildPayload;
import static io.cloudslang.content.openstack.builders.QueryParamsBuilder.buildQueryParams;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.AMPERSAND;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.COLON;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.EQUAL;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.factory.Headers.setHeaders;
import static io.cloudslang.content.openstack.factory.Path.getPath;
import static io.cloudslang.content.openstack.factory.Uri.getUri;
import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

public class InputsUtil {
    private InputsUtil() {
    }

    public static void setupApiCall(HttpClientInputs httpClientInputs, InputsWrapper wrapper) throws MalformedURLException, OpenstackException {
        String hostname = buildEndpoint(wrapper);
        String queryParams = buildQueryParams(wrapper);

        String url = of(hostname)
                .filter(f -> hostname.endsWith(SLASH))
                .map(s -> join(hostname.substring(0, hostname.length() - 1), queryParams))
                .orElse(join(hostname, queryParams));

        httpClientInputs.setUrl(url);

        setHeaders(wrapper);

        String payload = buildPayload(wrapper);

        ofNullable(payload)
                .filter(StringUtils::isNotEmpty)
                .ifPresent(p -> httpClientInputs.setBody(payload));
    }

    public static String getQueryParamsUri(Map<String, String> queryParamsMap) {
        return ofNullable(queryParamsMap)
                .filter(f -> !queryParamsMap.isEmpty())
                .map(s -> {
                    String queryParamsString = appendQueryParamsEntries(queryParamsMap);

                    return queryParamsString.substring(0, queryParamsString.length() - 1);
                })
                .orElse(EMPTY);
    }

    public static <E extends Enum<E>> String buildErrorMessage(Class<E> classOfT) {
        final StringBuilder sb = new StringBuilder();

        stream(classOfT.getEnumConstants())
                .forEach(entry -> concatenateEnumStringValues(sb, entry));

        String errorMessage = sb.toString();

        return of(errorMessage)
                .filter(StringUtils::isNotEmpty)
                .map(s -> errorMessage.substring(0, errorMessage.length() - 2))
                .orElse(EMPTY);
    }

    static String buildEndpoint(InputsWrapper wrapper) throws MalformedURLException, OpenstackException {
        URL url = new URL(wrapper.getCommonInputsBuilder().getEndpoint());

        StringJoiner sj = new StringJoiner(COLON);
        sj.add(url.toString());
        sj.add(getPath(wrapper));

        return appendIfMissing(sj.toString(), SLASH) + getUri(wrapper);
    }

    private static <E extends Enum<E>> void concatenateEnumStringValues(StringBuilder sb, E e) {
        if (safeCastOrNull(e, ComputeApi.class) != null) {
            sb
                    .append(((ComputeApi) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, AutoDiskConfig.class) != null) {
            sb
                    .append(((AutoDiskConfig) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, AuthenticationMethod.class) != null) {
            sb
                    .append(((AuthenticationMethod) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, IdentityApi.class) != null) {
            sb
                    .append(((IdentityApi) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, ServersApi.class) != null) {
            sb
                    .append(((ServersApi) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, AutoDiskConfig.class) != null) {
            sb
                    .append(((AutoDiskConfig) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, LockedBy.class) != null) {
            sb
                    .append(((LockedBy) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, PowerState.class) != null) {
            sb
                    .append(((PowerState) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, VmState.class) != null) {
            sb
                    .append(e.name().toLowerCase())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, SortKey.class) != null) {
            sb
                    .append(((SortKey) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        } else if (safeCastOrNull(e, Status.class) != null) {
            sb
                    .append(((Status) e).getValue())
                    .append(COMMA_DELIMITER + BLANK_SPACE);
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <T, E> E safeCastOrNull(final T value, final Class<E> targetType) {
        return ofNullable(targetType)
                .filter(f -> targetType.isInstance(value))
                .flatMap(e -> of((E) value))
                .orElse(null);
    }

    private static String appendQueryParamsEntries(Map<String, String> queryParamsMap) {
        StringBuilder sb = new StringBuilder();

        queryParamsMap.entrySet().stream()
                .filter(f -> isNotBlank(f.getValue()))
                .forEach(entry -> sb.append(join(entry.getKey(), EQUAL, entry.getValue(), AMPERSAND)));

        return sb.toString();
    }
}
