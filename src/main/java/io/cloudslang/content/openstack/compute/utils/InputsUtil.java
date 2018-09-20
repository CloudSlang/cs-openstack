package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.compute.entities.Api;
import io.cloudslang.content.openstack.compute.entities.identity.AuthenticationMethod;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import java.net.MalformedURLException;
import java.net.URL;

import static io.cloudslang.content.constants.OtherValues.COMMA_DELIMITER;
import static io.cloudslang.content.openstack.compute.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.compute.factory.UriFactory.getUri;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.join;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String buildUrl(InputsWrapper wrapper) throws MalformedURLException, OpenstackException {
        return new URL(wrapper.getCommonInputsBuilder().getEndpoint()).toString() + getUri(wrapper);
    }

    public static <T extends Enum<T>> String getEnumValidValuesString(Class<T> classOfT) {
        final StringBuilder sb = new StringBuilder();

        stream(classOfT.getEnumConstants())
                .forEach(t -> appendValue(sb, t));

        return isBlank(sb.toString()) ? EMPTY : sb.deleteCharAt(sb.length() - 2).toString().trim();
    }

    private static <T extends Enum<T>> void appendValue(StringBuilder sb, T t) {
        if (Api.class.getCanonicalName().equalsIgnoreCase(t.getClass().getCanonicalName())) {
            sb.append(((Api) t).getValue()).append(join(COMMA_DELIMITER, BLANK_SPACE));
        } else if (AuthenticationMethod.class.getCanonicalName().equalsIgnoreCase(t.getClass().getCanonicalName())) {
            sb.append(((AuthenticationMethod) t).getValue()).append(join(COMMA_DELIMITER, BLANK_SPACE));
        }
    }
}
