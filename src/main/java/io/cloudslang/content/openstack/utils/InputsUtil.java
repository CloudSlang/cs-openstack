package io.cloudslang.content.openstack.utils;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.AMPERSAND;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.COLON;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.EQUAL;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.factory.Prefix.getPrefix;
import static io.cloudslang.content.openstack.factory.Uri.getUri;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String buildUrl(InputsWrapper wrapper) throws MalformedURLException, OpenstackException {
        URL url = new URL(wrapper.getCommonInputsBuilder().getEndpoint());

        StringJoiner sj = new StringJoiner(COLON);
        sj.add(url.toString());
        sj.add(getPrefix(wrapper));

        return appendIfMissing(sj.toString(), SLASH) + getUri(wrapper);
    }

    public static String handleQueryUrl(String url, String queryParams) {
        return url.endsWith(SLASH) ?
                join(url.substring(0, url.length() - 1), queryParams) : join(url, queryParams);
    }

    public static <E extends Enum<E>> String buildErrorMessage(Class<E> classOfT) {
        final StringBuilder sb = new StringBuilder();

        stream(classOfT.getEnumConstants())
                .forEach(e -> concatenateEntries(sb, e, COMMA_DELIMITER + BLANK_SPACE));

        String errorMessage = sb.toString();

        return isBlank(errorMessage) ? EMPTY : errorMessage.substring(0, errorMessage.length() - 2);
    }

    @SuppressWarnings("SameParameterValue")
    private static <E extends Enum<E>> void concatenateEntries(StringBuilder sb, E e, String delimiter) {
        if (safeCastOrNull(e, ComputeApi.class) != null) {
            sb
                    .append(((ComputeApi) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, AutoDiskConfig.class) != null) {
            sb
                    .append(((AutoDiskConfig) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, AuthenticationMethod.class) != null) {
            sb
                    .append(((AuthenticationMethod) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, IdentityApi.class) != null) {
            sb
                    .append(((IdentityApi) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, ServersApi.class) != null) {
            sb
                    .append(((ServersApi) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, AutoDiskConfig.class) != null) {
            sb
                    .append(((AutoDiskConfig) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, LockedBy.class) != null) {
            sb
                    .append(((LockedBy) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, PowerState.class) != null) {
            sb
                    .append(valueOf(((PowerState) e).getValue()))
                    .append(delimiter);
        } else if (safeCastOrNull(e, VmState.class) != null) {
            sb
                    .append(e.name().toLowerCase())
                    .append(delimiter);
        } else if (safeCastOrNull(e, SortKey.class) != null) {
            sb
                    .append(((SortKey) e).getValue())
                    .append(delimiter);
        } else if (safeCastOrNull(e, Status.class) != null) {
            sb
                    .append(((Status) e).getValue())
                    .append(delimiter);
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <T, E> E safeCastOrNull(final T value, final Class<E> targetType) {
        return targetType == null || !targetType.isInstance(value) ? null :
                Optional
                        .of((E) value)
                        .get();
    }

    public static String getQueryParamsString(Map<String, String> queryParamsMap) {
        if (queryParamsMap == null || queryParamsMap.isEmpty()) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();

        queryParamsMap.entrySet().stream()
                .filter(f -> isNotBlank(f.getValue()))
                .forEach(entry -> sb.append(join(entry.getKey(), EQUAL, entry.getValue(), AMPERSAND)));

        String queryParamsString = sb.toString();

        return queryParamsString.substring(0, queryParamsString.length() - 1);
    }
}
