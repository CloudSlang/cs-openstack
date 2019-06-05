package io.cloudslang.content.openstack.service;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.httpclient.services.HttpClientService;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.utils.OutputUtilities;
import io.vavr.control.Try;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static io.cloudslang.content.openstack.entities.Constants.Requests.THREADS_NUMBER;
import static io.cloudslang.content.openstack.factory.InputsWrapperFactory.buildWrapper;
import static io.cloudslang.content.openstack.utils.InputsUtil.setupApiCall;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class OpenstackService {
    private final ExecutorService executorService = newFixedThreadPool(THREADS_NUMBER);

    @SafeVarargs
    public final <T> Map<String, String> execute(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) {
        InputsWrapper wrapper = buildWrapper(httpClientInputs, commonInputsBuilder, builders);

        return Try
                .of(() -> {
                    setupApiCall(httpClientInputs, wrapper);

                    return asyncCall(httpClientInputs);
                })
                .onFailure(OutputUtilities::getFailureResultsMap)
                .get();
    }

    private Map<String, String> asyncCall(HttpClientInputs httpClientInputs) throws InterruptedException, ExecutionException {
        return CompletableFuture
                .supplyAsync(() -> new HttpClientService().execute(httpClientInputs), executorService)
                .exceptionally(OutputUtilities::getFailureResultsMap)
                .get();
    }
}
