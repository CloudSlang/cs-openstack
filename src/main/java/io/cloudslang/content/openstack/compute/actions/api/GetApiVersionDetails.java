package io.cloudslang.content.openstack.compute.actions.api;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType;
import io.cloudslang.content.constants.ReturnCodes;
import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.service.OpenstackService;

import java.util.Map;

import static io.cloudslang.content.constants.OutputNames.EXCEPTION;
import static io.cloudslang.content.constants.OutputNames.RETURN_CODE;
import static io.cloudslang.content.constants.OutputNames.RETURN_RESULT;
import static io.cloudslang.content.constants.ResponseNames.FAILURE;
import static io.cloudslang.content.constants.ResponseNames.SUCCESS;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.CONNECT_TIMEOUT;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.KEEP_ALIVE;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.KEYSTORE;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.KEYSTORE_PASSWORD;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.PROXY_HOST;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.PROXY_PASSWORD;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.PROXY_PORT;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.PROXY_USERNAME;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.SOCKET_TIMEOUT;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.TRUST_ALL_ROOTS;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.TRUST_KEYSTORE;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.TRUST_PASSWORD;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.USE_COOKIES;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.X509_HOSTNAME_VERIFIER;
import static io.cloudslang.content.openstack.builders.HttpClientInputsBuilder.buildHttpClientInputs;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.GET_API_VERSION_DETAILS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.DEFAULT_COMPUTE_VERSION;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Api.API_VERSION;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.ENDPOINT;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.VERSION;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.http.client.methods.HttpGet.METHOD_NAME;

public class GetApiVersionDetails {
    /**
     * Fetches all the information about all known major API versions in the deployment.
     * Links to more specific information will be provided for each API version, as well as information about supported
     * min and max microversions.
     * <p>
     * Normal Response Codes: 200.
     * Reference URL: https://developer.openstack.org/api-ref/compute/
     *
     * @param endpoint             Endpoint to which request will be sent. A valid endpoint will be formatted as it shows
     *                             in bellow example.
     *                             Example: "http://mycompute.pvt/"
     * @param proxyHost            Optional - proxy server used to connect to Openstack API. If empty no proxy will be used.
     * @param proxyPort            Optional - proxy server port. You must either specify values for both proxyHost and
     *                             proxyPort inputs or leave them both empty.
     * @param proxyUsername        Optional - proxy server user name.
     * @param proxyPassword        Optional - proxy server password associated with the proxyUsername input value.
     * @param trustAllRoots        Optional - specifies whether to enable weak security over SSL/TSL. A certificate is
     *                             trusted even if no trusted certification authority issued it.
     *                             Valid values: "true", "false"
     *                             Default value: "true"
     * @param x509HostnameVerifier Optional - specifies the way the server hostname must match a domain name in the subject's
     *                             Common Name (CN) or subjectAltName field of the X.509 certificate. Set this to "allow_all"
     *                             to skip any checking. For the value "browser_compatible" the hostname verifier works
     *                             the same way as Curl and Firefox. The hostname must match either the first CN, or any
     *                             of the subject-alts. A wildcard can occur in the CN, and in any of the subject-alts.
     *                             The only difference between "browser_compatible" and "strict" is that a wildcard (such
     *                             as "*.foo.com") with "browser_compatible" matches all subdomains, including "a.b.foo.com".
     *                             Valid values: "strict", "browser_compatible", "allow_all"
     *                             Default value: "allow_all"
     * @param trustKeystore        Optional - pathname of the Java TrustStore file. This contains certificates from other
     *                             parties that you expect to communicate with, or from Certificate Authorities that you
     *                             trust to identify other parties. If the protocol (specified by the "url") is not "https"
     *                             or if trustAllRoots is "true" this input is ignored.
     *                             Default value: ../java/lib/security/cacerts
     *                             Format: Java KeyStore (JKS)
     * @param trustPassword        Optional - password associated with the TrustStore file. If trustAllRoots is "false"
     *                             and trustKeystore is empty, trustPassword default will be supplied.
     *                             Default value: "changeit"
     * @param keystore             Optional - pathname of the Java KeyStore file. You only need this if the server requires
     *                             client authentication. If the protocol (specified by the "url") is not "https" or if
     *                             trustAllRoots is "true" this input is ignored.
     *                             Format: Java KeyStore (JKS)
     *                             Default value: ../java/lib/security/cacerts.
     * @param keystorePassword     Optional - password associated with the KeyStore file. If trustAllRoots is "false" and
     *                             keystore is empty, keystorePassword default will be supplied.
     *                             Default value: "changeit"
     * @param connectTimeout       Optional - time to wait for a connection to be established, in seconds. A timeout value
     *                             of "0" represents an infinite timeout.
     *                             Default value: "0"
     * @param socketTimeout        Optional - timeout for waiting for data (a maximum period inactivity between two
     *                             consecutive data packets), in seconds. A socketTimeout value of "0" represents an
     *                             infinite timeout.
     *                             Default value: "0"
     * @param useCookies           Optional - specifies whether to enable cookie tracking or not. Cookies are stored between
     *                             consecutive calls in a serializable session object therefore they will be available on
     *                             a branch level. If you specify a non-boolean value, the default value is used.
     *                             Valid values: "true", "false"
     *                             Default value: "true"
     * @param keepAlive            Optional - specifies whether to create a shared connection that will be used in subsequent
     *                             calls. If keepAlive is "false", the already open connection will be used and after
     *                             execution it will close it.
     *                             Valid values: "true", "false"
     *                             Default value: "true"
     * @param version              Optional - specifies the Openstack API version.
     *                             Default value: "2.0"
     *                             Notes: The maximum microversion supported by each release varies.
     *                             Please reference: https://docs.openstack.org/nova/latest/reference/api-microversion-history.html
     *                             for API microversion history details.
     * @param apiVersion           The API version to obtain details for
     *                             Examples: "v2.0", "v2.1"
     *                             Default value: "v2.0"
     * @return A map with strings as keys and strings as values that contains: outcome of the action (or failure message
     * and the exception if there is one), returnCode of the operation
     */
    @Action(name = "Get ComputeApis Version Details",
            outputs = {
                    @Output(RETURN_CODE),
                    @Output(RETURN_RESULT),
                    @Output(EXCEPTION)
            },
            responses = {
                    @Response(text = SUCCESS, field = RETURN_CODE, value = ReturnCodes.SUCCESS,
                            matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED),
                    @Response(text = FAILURE, field = RETURN_CODE, value = ReturnCodes.FAILURE,
                            matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, isOnFail = true)
            })
    public Map<String, String> execute(@Param(value = ENDPOINT, required = true) String endpoint,
                                       @Param(value = PROXY_HOST) String proxyHost,
                                       @Param(value = PROXY_PORT) String proxyPort,
                                       @Param(value = PROXY_USERNAME) String proxyUsername,
                                       @Param(value = PROXY_PASSWORD, encrypted = true) String proxyPassword,
                                       @Param(value = TRUST_ALL_ROOTS) String trustAllRoots,
                                       @Param(value = X509_HOSTNAME_VERIFIER) String x509HostnameVerifier,
                                       @Param(value = TRUST_KEYSTORE) String trustKeystore,
                                       @Param(value = TRUST_PASSWORD, encrypted = true) String trustPassword,
                                       @Param(value = KEYSTORE) String keystore,
                                       @Param(value = KEYSTORE_PASSWORD, encrypted = true) String keystorePassword,
                                       @Param(value = CONNECT_TIMEOUT) String connectTimeout,
                                       @Param(value = SOCKET_TIMEOUT) String socketTimeout,
                                       @Param(value = USE_COOKIES) String useCookies,
                                       @Param(value = KEEP_ALIVE) String keepAlive,
                                       @Param(value = VERSION) String version,
                                       @Param(value = API_VERSION, required = true) String apiVersion) {

        HttpClientInputs httpClientInputs = buildHttpClientInputs(proxyHost, proxyPort, proxyUsername, proxyPassword,
                trustAllRoots, x509HostnameVerifier, trustKeystore, trustPassword, keystore, keystorePassword,
                connectTimeout, socketTimeout, useCookies, keepAlive, METHOD_NAME);

        final CommonInputsBuilder commonInputsBuilder = new CommonInputsBuilder.Builder()
                .withEndpoint(endpoint)
                .withAction(GET_API_VERSION_DETAILS)
                .withApi(API)
                .withVersion(defaultIfEmpty(version, DEFAULT_COMPUTE_VERSION))
                .build();

        final ApiInputsBuilder apiInputsBuilder = new ApiInputsBuilder.Builder()
                .withApiVersion(apiVersion)
                .build();

        return new OpenstackService().execute(httpClientInputs, commonInputsBuilder, apiInputsBuilder);
    }
}
