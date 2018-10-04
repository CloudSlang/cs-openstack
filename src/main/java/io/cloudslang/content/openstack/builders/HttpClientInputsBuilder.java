package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;

import static io.cloudslang.content.httpclient.build.auth.AuthTypes.ANONYMOUS;
import static io.cloudslang.content.openstack.entities.Constants.Values.DEFAULT_TIMEOUT_VALUE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
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

        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            httpClientInputs.setProxyHost(proxyHost);
            httpClientInputs.setProxyPort(proxyPort);
        }

        if (isNotBlank(proxyUsername) && isNotBlank(proxyPassword)) {
            httpClientInputs.setProxyUsername(proxyUsername);
            httpClientInputs.setProxyPassword(proxyPassword);
        }

        if (isNotBlank(trustKeystore) && isNotBlank(trustPassword)) {
            httpClientInputs.setTrustKeystore(trustKeystore);
            httpClientInputs.setTrustPassword(trustPassword);
        }

        if (isNotBlank(keystore) && isNotBlank(keystorePassword)) {
            httpClientInputs.setKeystore(keystore);
            httpClientInputs.setKeystorePassword(keystorePassword);
        }

        if (isBlank(x509HostnameVerifier) || !asList("allow_all", "browser_compatible", "strict").contains(x509HostnameVerifier)) {
            httpClientInputs.setX509HostnameVerifier("allow_all");
        } else {
            httpClientInputs.setX509HostnameVerifier(x509HostnameVerifier);
        }

        httpClientInputs.setTrustAllRoots(defaultIfEmpty(trustAllRoots, valueOf(FALSE)));
        httpClientInputs.setConnectTimeout(defaultIfEmpty(connectTimeout, DEFAULT_TIMEOUT_VALUE));
        httpClientInputs.setSocketTimeout(defaultIfEmpty(socketTimeout, DEFAULT_TIMEOUT_VALUE));
        httpClientInputs.setUseCookies(defaultIfEmpty(useCookies, valueOf(FALSE)));
        httpClientInputs.setKeepAlive(defaultIfEmpty(keepAlive, valueOf(TRUE)));
        httpClientInputs.setQueryParamsAreURLEncoded(valueOf(FALSE));

        httpClientInputs.setAuthType(ANONYMOUS);
        httpClientInputs.setContentType(APPLICATION_JSON.getMimeType());
        httpClientInputs.setRequestCharacterSet(UTF_8);

        return httpClientInputs;
    }
}
