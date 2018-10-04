package io.cloudslang.content.openstack.service;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.httpclient.services.HttpClientService;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.net.MalformedURLException;
import java.util.Map;

import static io.cloudslang.content.openstack.builders.PayloadBuilder.buildPayload;
import static io.cloudslang.content.openstack.builders.QueryParamsBuilder.buildQueryParams;
import static io.cloudslang.content.openstack.factory.HeadersFactory.setHeaders;
import static io.cloudslang.content.openstack.factory.InputsWrapperFactory.buildWrapper;
import static io.cloudslang.content.openstack.utils.InputsUtil.buildUrl;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

public class OpenstackService {
    @SafeVarargs
    public final <T> Map<String, String> execute(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) throws MalformedURLException, OpenstackException {
        InputsWrapper wrapper = buildWrapper(httpClientInputs, commonInputsBuilder, builders);

        httpClientInputs.setUrl(join(buildUrl(wrapper), buildQueryParams(wrapper)));
        setHeaders(wrapper);

        String payload = buildPayload(wrapper);
        if (isNotBlank(payload)) {
            httpClientInputs.setBody(buildPayload(wrapper));
        }

        return new HttpClientService().execute(httpClientInputs);
    }
}
