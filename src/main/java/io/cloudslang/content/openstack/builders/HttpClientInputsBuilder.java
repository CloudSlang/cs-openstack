package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;

import static io.cloudslang.content.httpclient.build.auth.AuthTypes.ANONYMOUS;
import static io.cloudslang.content.openstack.entities.Constants.Headers.ALLOW_ALL;
import static io.cloudslang.content.openstack.entities.Constants.Headers.BROWSER_COMPATIBLE;
import static io.cloudslang.content.openstack.entities.Constants.Headers.STRICT;
import static io.cloudslang.content.openstack.entities.Constants.Values.DEFAULT_TIMEOUT_VALUE;
import static io.cloudslang.content.openstack.validators.Validators.bothValuesArePresent;
import static io.cloudslang.content.openstack.validators.Validators.isPositiveInt;
import static io.cloudslang.content.openstack.validators.Validators.isValidBoolean;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class HttpClientInputsBuilder {
    private HttpClientInputsBuilder() {
    }

    public static HttpClientInputs buildHttpClientInputs(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword,
                                                         String trustAllRoots, String x509HostnameVerifier, String trustKeystore,
                                                         String trustPassword, String keystore, String keystorePassword,
                                                         String connectTimeout, String socketTimeout, String useCookies,
                                                         String keepAlive, String method) {
        HttpClientInputs httpClientInputs = new HttpClientInputs();

        httpClientInputs.setMethod(method);

        httpClientInputs.setTrustAllRoots(ofNullable(trustAllRoots)
                .filter(f -> isValidBoolean(trustAllRoots))
                .orElse(valueOf(FALSE)));

        httpClientInputs.setConnectTimeout(ofNullable(connectTimeout)
                .filter(f -> isPositiveInt(connectTimeout))
                .orElse(DEFAULT_TIMEOUT_VALUE));

        httpClientInputs.setSocketTimeout(ofNullable(socketTimeout)
                .filter(f -> isPositiveInt(socketTimeout))
                .orElse(DEFAULT_TIMEOUT_VALUE));

        httpClientInputs.setUseCookies(ofNullable(useCookies)
                .filter(f -> isValidBoolean(useCookies))
                .orElse(valueOf(FALSE)));

        httpClientInputs.setKeepAlive(ofNullable(keepAlive)
                .filter(f -> isValidBoolean(keepAlive))
                .orElse(valueOf(TRUE)));

        httpClientInputs.setX509HostnameVerifier(ofNullable(x509HostnameVerifier)
                .filter(f -> asList(ALLOW_ALL, BROWSER_COMPATIBLE, STRICT).contains(x509HostnameVerifier))
                .orElse(ALLOW_ALL));

        of(bothValuesArePresent(proxyHost, proxyPort))
                .ifPresent(set -> {
                    httpClientInputs.setProxyHost(proxyHost);
                    httpClientInputs.setProxyPort(proxyPort);
                });

        of(bothValuesArePresent(proxyUsername, proxyPassword))
                .ifPresent(set -> {
                    httpClientInputs.setProxyUsername(proxyUsername);
                    httpClientInputs.setProxyPassword(proxyPassword);
                });

        of(bothValuesArePresent(trustKeystore, trustPassword))
                .ifPresent(set -> {
                    httpClientInputs.setTrustKeystore(trustKeystore);
                    httpClientInputs.setTrustPassword(trustPassword);
                });

        of(bothValuesArePresent(keystore, keystorePassword))
                .ifPresent(set -> {
                    httpClientInputs.setKeystore(keystore);
                    httpClientInputs.setKeystorePassword(keystorePassword);
                });

        httpClientInputs.setQueryParamsAreURLEncoded(valueOf(TRUE));
        httpClientInputs.setAuthType(ANONYMOUS);
        httpClientInputs.setContentType(APPLICATION_JSON.getMimeType());
        httpClientInputs.setRequestCharacterSet(UTF_8);

        return httpClientInputs;
    }
}
