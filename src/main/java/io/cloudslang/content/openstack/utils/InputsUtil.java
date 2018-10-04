package io.cloudslang.content.openstack.utils;

import io.cloudslang.content.openstack.compute.entities.ComputeApi;
import io.cloudslang.content.openstack.compute.entities.ServersApi;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.entities.AuthenticationMethod;
import io.cloudslang.content.openstack.identity.entities.IdentityApi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.StringJoiner;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.COLON;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.entities.Constants.Values.VERSION_THRESHOLD_FLOAT;
import static io.cloudslang.content.openstack.factory.PrefixFactory.getApiPrefix;
import static io.cloudslang.content.openstack.factory.UriFactory.getUri;
import static java.lang.Float.parseFloat;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String buildUrl(InputsWrapper wrapper) throws MalformedURLException, OpenstackException {
        URL url = new URL(wrapper.getCommonInputsBuilder().getEndpoint());

        StringJoiner sj = new StringJoiner(COLON);
        sj.add(url.toString());
        sj.add(getApiPrefix(wrapper));

        return appendIfMissing(sj.toString(), SLASH) + getUri(wrapper);
    }

    public static boolean isVersionGreaterOrEqualThanThreshold(String input) {
        if (isNotBlank(input)) {
            try {
                return parseFloat(input) >= VERSION_THRESHOLD_FLOAT; // micro-version threshold for tracking requests
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        return false;
    }

    public static <E extends Enum<E>> String buildErrorMessage(Class<E> classOfT) {
        final StringBuilder sb = new StringBuilder();

        stream(classOfT.getEnumConstants())
                .forEach(e -> concatenate(sb, e, COMMA_DELIMITER + BLANK_SPACE));

        String errorMessage = sb.toString();

        return isBlank(errorMessage) ? EMPTY : errorMessage.substring(0, errorMessage.length() - 2);
    }

    @SuppressWarnings("SameParameterValue")
    private static <E extends Enum<E>> void concatenate(StringBuilder sb, E e, String delimiter) {
        if (safeCastOrNull(e, ComputeApi.class) != null) {
            sb
                    .append(((ComputeApi) e).getValue())
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
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <T, E> E safeCastOrNull(final T value, final Class<E> targetType) {
        return targetType == null || !targetType.isInstance(value) ? null :
                Optional
                        .of((E) value)
                        .get();
    }
}
