package io.cloudslang.content.openstack.identity.actions;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType;
import io.cloudslang.content.constants.ReturnCodes;
import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;
import io.cloudslang.content.openstack.identity.entities.Auth;
import io.cloudslang.content.openstack.identity.entities.AuthenticationMethod;
import io.cloudslang.content.openstack.identity.entities.Domain;
import io.cloudslang.content.openstack.identity.entities.Identity;
import io.cloudslang.content.openstack.identity.entities.Password;
import io.cloudslang.content.openstack.identity.entities.User;
import io.cloudslang.content.openstack.identity.responses.AuthenticationResponse;
import io.cloudslang.content.openstack.service.OpenstackService;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static io.cloudslang.content.constants.OutputNames.EXCEPTION;
import static io.cloudslang.content.constants.OutputNames.RETURN_CODE;
import static io.cloudslang.content.constants.OutputNames.RETURN_RESULT;
import static io.cloudslang.content.constants.ResponseNames.FAILURE;
import static io.cloudslang.content.constants.ResponseNames.SUCCESS;
import static io.cloudslang.content.httpclient.build.auth.AuthTypes.ANONYMOUS;
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
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.ENDPOINT;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.VERSION;
import static io.cloudslang.content.openstack.handlers.ResponseHandler.getHeaderValue;
import static io.cloudslang.content.openstack.handlers.ResponseHandler.handleResponse;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.Constants.Api.IDENTITY;
import static io.cloudslang.content.openstack.identity.entities.Constants.Headers.X_SUBJECT_TOKEN;
import static io.cloudslang.content.openstack.identity.entities.Constants.QueryParams.NO_CATALOG;
import static io.cloudslang.content.openstack.identity.entities.Constants.Responses.EXPIRES_AT;
import static io.cloudslang.content.openstack.identity.entities.Constants.Responses.NEVER;
import static io.cloudslang.content.openstack.identity.entities.Constants.Responses.TOKEN;
import static io.cloudslang.content.openstack.identity.entities.Constants.Versions.DEFAULT_IDENTITY_VERSION;
import static io.cloudslang.content.openstack.identity.entities.Inputs.DOMAIN_ID;
import static io.cloudslang.content.openstack.identity.entities.Inputs.DOMAIN_NAME;
import static io.cloudslang.content.openstack.identity.entities.Inputs.ID;
import static io.cloudslang.content.openstack.identity.entities.Inputs.PASSWORD;
import static io.cloudslang.content.openstack.identity.entities.Inputs.USERNAME;
import static io.cloudslang.content.openstack.identity.utils.IdentityUtils.buildDomain;
import static io.cloudslang.content.openstack.identity.utils.IdentityUtils.buildUser;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.http.client.methods.HttpPost.METHOD_NAME;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class PasswordAuthenticationWithUnscopedAuthorization {
    private static final String RESPONSE_HEADERS = "responseHeaders";

    /**
     * Authenticates an identity and generates a token. Uses the password authentication method. Authorization is unscoped.
     * The request body must include a payload that specifies the authentication method, which is password, and the user,
     * by ID or name, and password credentials.
     * <p>
     * Normal response codes: 201.
     * <p>
     * Authentication errors:
     * <p>
     * 400 - Bad Request: Some content in the request was invalid.
     * 401 - Unauthorized: User must authenticate before making a request.
     * 403 - Forbidden:	Policy does not allow current user to do this operation.
     * 404 - Not Found:	The requested resource could not be found.
     * 405 - Method Not Allowed: Method is not valid for this endpoint.
     * 409 - Conflict: This operation conflicted with another operation on this resource.
     * 413 - Request Entity Too Large: The request is larger than the server is willing or able to process.
     * 415 - Unsupported Media Type: The request entity has a media type which the server or resource does not support.
     * 503 - Service Unavailable: Service is not available. This is mostly caused by service configuration errors which
     * prevents the service from successful start up.
     * <p>
     * Reference URL: https://developer.openstack.org/api-ref/identity/v3/#authentication-and-token-management
     *
     * @param endpoint             Endpoint to which request will be sent. A valid endpoint will be formatted as it shows
     *                             in bellow example.
     *                             Example: "http://mycompute.pvt::5000/"
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
     * @param version              The Identity API version
     *                             Examples: "v2", "v3"
     *                             Default value: "v3"
     * @param username             Optional - User name. Required if you do not specify the ID of the user. If you specify
     *                             the user name, you must also specify the domain, by ID or name. Specify either username
     *                             or userId but not both.
     * @param id                   Optional - ID of the user. Required if you do not specify the user name. Specify
     *                             either username or userId but not both.
     * @param password             Optional - User password needed for authentication.
     * @param domainId             Optional - Domain id where user belongs to. If you specify the user name, you must
     *                             also specify the domain, either by ID or name but not both.
     * @param domainName           Optional - Domain name where user belongs to. If you specify the user name, you
     *                             must also specify the domain, either by ID or name but not both.
     * @param noCatalog            Optional - (Since v3.1) Authentication response excludes the service catalog. By default,
     *                             the response includes the service catalog.
     *                             Valid values: "true", "false"
     *                             Default value: "true"
     * @return A map with strings as keys and strings as values that contains: outcome of the action (or failure message
     * and the exception if there is one), returnCode of the operation
     */
    @Action(name = "Password authentication with unscoped authorization",
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
                                       @Param(value = ID) String id,
                                       @Param(value = USERNAME) String username,
                                       @Param(value = PASSWORD) String password,
                                       @Param(value = DOMAIN_ID) String domainId,
                                       @Param(value = DOMAIN_NAME) String domainName,
                                       @Param(value = NO_CATALOG) String noCatalog) {
        try {
            HttpClientInputs httpClientInputs = buildHttpClientInputs(proxyHost, proxyPort, proxyUsername, proxyPassword,
                    trustAllRoots, x509HostnameVerifier, trustKeystore, trustPassword, keystore, keystorePassword, connectTimeout,
                    socketTimeout, useCookies, keepAlive, METHOD_NAME, ANONYMOUS, APPLICATION_JSON.getMimeType());

            final CommonInputsBuilder commonInputsBuilder = new CommonInputsBuilder.Builder()
                    .withEndpoint(endpoint)
                    .withAction(PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION)
                    .withApi(IDENTITY)
                    .withVersion(defaultIfEmpty(version, DEFAULT_IDENTITY_VERSION))
                    .build();

            final Domain domain = buildDomain(domainId, domainName);

            final User user = buildUser(domain, id, username, password);

            final Password passwd = new Password.Builder()
                    .withUser(user)
                    .build();

            List<String> methodsList = singletonList(AuthenticationMethod.PASSWORD.getValue());

            final Identity identity = new Identity.Builder()
                    .withMethods(methodsList)
                    .withPassword(passwd)
                    .build();

            final Auth auth = new Auth.Builder()
                    .withIdentity(identity)
                    .build();

            final IdentityInputsBuilder identityInputsBuilder = new IdentityInputsBuilder.Builder()
                    .withAuth(auth)
                    .withNoCatalog(noCatalog)
                    .build();

            Map<String, String> response = new OpenstackService().execute(httpClientInputs, commonInputsBuilder, identityInputsBuilder);

            String token = getHeaderValue(response.get(RESPONSE_HEADERS), X_SUBJECT_TOKEN);
            if (isNotBlank(token)) {
                response.put(TOKEN, token);
            }

            String expiresAt = handleResponse(response.get(RETURN_RESULT), AuthenticationResponse.class);
            if (isNotBlank(expiresAt)) {
                response.put(EXPIRES_AT, expiresAt);
            } else {
                response.put(EXPIRES_AT, NEVER);
            }

            return response;
        } catch (MalformedURLException | OpenstackException exception) {
            return getFailureResultsMap(exception);
        }
    }
}
