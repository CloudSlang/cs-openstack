package io.cloudslang.content.openstack.utils;

import io.cloudslang.content.openstack.compute.entities.ComputeApi;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.entities.AuthenticationMethod;
import io.cloudslang.content.openstack.identity.entities.IdentityApi;

import java.net.MalformedURLException;
import java.net.URL;
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
import static org.apache.commons.lang3.StringUtils.join;

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

    public static <T extends Enum<T>> String getEnumValidValuesString(Class<T> classOfT) {
        final StringBuilder sb = new StringBuilder();

        stream(classOfT.getEnumConstants())
                .forEach(t -> appendTo(sb, t));

        return isBlank(sb.toString()) ? EMPTY : sb.deleteCharAt(sb.length() - 2).toString().trim();
    }

    private static <T extends Enum<T>> void appendTo(StringBuilder sb, T t) {
        String messageSeparator = join(COMMA_DELIMITER, BLANK_SPACE);

        if (ComputeApi.class.getCanonicalName().equalsIgnoreCase(t.getClass().getCanonicalName())) {
            sb.append(((ComputeApi) t).getValue()).append(messageSeparator);
        } else if (AuthenticationMethod.class.getCanonicalName().equalsIgnoreCase(t.getClass().getCanonicalName())) {
            sb.append(((AuthenticationMethod) t).getValue()).append(messageSeparator);
        } else if (IdentityApi.class.getCanonicalName().equalsIgnoreCase(t.getClass().getCanonicalName())) {
            sb.append(((IdentityApi) t).getValue()).append(messageSeparator);
        }
    }
}
