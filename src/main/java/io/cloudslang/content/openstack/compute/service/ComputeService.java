package io.cloudslang.content.openstack.compute.service;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.httpclient.services.HttpClientService;
import io.cloudslang.content.openstack.compute.builders.CommonInputs;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import java.net.MalformedURLException;
import java.util.Map;

import static io.cloudslang.content.openstack.compute.builders.InputsWrapperBuilder.buildWrapper;
import static io.cloudslang.content.openstack.compute.builders.PayloadBuilder.buildPayload;
import static io.cloudslang.content.openstack.compute.factory.HeadersFactory.setHeaders;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.buildUrl;

public class ComputeService {
    @SafeVarargs
    public final <T> Map<String, String> execute(HttpClientInputs httpClientInputs, CommonInputs commonInputs, T... builders) throws MalformedURLException, OpenstackException {
        InputsWrapper wrapper = buildWrapper(httpClientInputs, commonInputs, builders);

        httpClientInputs.setUrl(buildUrl(wrapper));

        setHeaders(wrapper);
        buildPayload(wrapper);

        return new HttpClientService().execute(httpClientInputs);
    }
}
