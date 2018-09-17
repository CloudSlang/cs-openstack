package io.cloudslang.content.openstack.compute.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.compute.entities.api.ListAllMajorVersionsResponse;
import io.cloudslang.content.openstack.compute.entities.api.Version;

import static io.cloudslang.content.openstack.compute.entities.Constants.Miscellaneous.BLANK_SPACE;
import static io.cloudslang.content.openstack.compute.entities.Constants.Miscellaneous.COMMA;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ResponseHandler {
    private static final Gson GSON = new GsonBuilder().create();

    private ResponseHandler() {
    }

    public static <T> String handleResponse(String input, Class<T> classOfT) {
        if (ListAllMajorVersionsResponse.class.getCanonicalName().equalsIgnoreCase(classOfT.getCanonicalName())) {
            //noinspection unchecked
            return handleApiResponse(GSON.fromJson(input, ListAllMajorVersionsResponse.class));
        }

        return null;
    }

    private static String handleApiResponse(ListAllMajorVersionsResponse listAllMajorVersionsResponse) {
        StringBuilder sb = new StringBuilder();
        for (Version version : listAllMajorVersionsResponse.getVersions()) {
            sb.append(version.getId()).append(COMMA).append(BLANK_SPACE);
        }

        return isBlank(sb.toString()) ? EMPTY : sb.deleteCharAt(sb.length() - 2).toString().trim();
    }
}
