package io.cloudslang.content.openstack.compute.builders;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class HttpClientInputsBuilder {
    private HttpClientInputsBuilder() {
    }

    public static HttpClientInputs buildHttpClient(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword,
                                                   String x509HostnameVerifier, String trustKeystore, String trustPassword,
                                                   String keystore, String keystorePassword) {
        HttpClientInputs httpClientInputs = new HttpClientInputs();

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

        return httpClientInputs;
    }
}
