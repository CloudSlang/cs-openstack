package io.cloudslang.content.openstack.compute.actions.servers;

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
import io.cloudslang.content.openstack.service.OpenstackService;

import java.net.MalformedURLException;
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
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_ALL_MAJOR_VERSIONS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.DEFAULT_COMPUTE_VERSION;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.ACCESS_IP_V4;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.ACCESS_IP_V6;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.ALL_TENANTS;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.AUTO_DISK_CONFIG;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.AVAILABILITY_ZONE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.CHANGES_BEFORE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.CHANGES_SINCE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.CONFIG_DRIVE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.CREATED_AT;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.DELETED;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.DESCRIPTION;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.FLAVOR;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.HOST;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.HOSTNAME;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.IMAGE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.IP;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.IP6;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.KERNEL_ID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.KEY_NAME;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.LAUNCHED_AT;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.LAUNCH_INDEX;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.LIMIT;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.LOCKED_BY;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.MARKER;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.NAME;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.NODE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.NOT_TAGS;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.NOT_TAGS_ANY;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.POWER_STATE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.PROGRESS;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.PROJECT_ID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.RAMDISK_ID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.RESERVATION_ID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.ROOT_DEVICE_NAME;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.SOFT_DELETED;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.SORT_DIR;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.SORT_KEY;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.STATUS;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.TAGS;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.TAGS_ANY;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.TASK_STATE;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.TERMINATED_AT;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.USER_ID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.UUID;
import static io.cloudslang.content.openstack.compute.entities.Inputs.Servers.VM_STATE;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.ENDPOINT;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.VERSION;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.http.client.methods.HttpGet.METHOD_NAME;

public class ListServers {
    /**
     * Lists IDs, names, and links for all servers.
     * <p>
     * Servers contain a status attribute that indicates the current server state. You can filter on the server status
     * when you complete a list servers request. The server status is returned in the response body. The possible server
     * status values are:
     * ACTIVE. The server is active.
     * BUILD. The server has not finished the original build process.
     * DELETED. The server is permanently deleted.
     * ERROR. The server is in error.
     * HARD_REBOOT. The server is hard rebooting. This is equivalent to pulling the power plug on a physical server,
     * plugging it back in, and rebooting it.
     * MIGRATING. The server is being migrated to a new host.
     * PASSWORD. The password is being reset on the server.
     * PAUSED. In a paused state, the state of the server is stored in RAM. A paused server continues to run in frozen
     * state.
     * REBOOT. The server is in a soft reboot state. A reboot command was passed to the operating system.
     * REBUILD. The server is currently being rebuilt from an image.
     * RESCUE. The server is in rescue mode. A rescue image is running with the original server image attached.
     * RESIZE. Server is performing the differential copy of data that changed during its initial copy. Server is down
     * for this stage.
     * REVERT_RESIZE. The resize or migration of a server failed for some reason. The destination server is being cleaned
     * up and the original source server is restarting.
     * SHELVED: The server is in shelved state. Depending on the shelve offload time, the server will be automatically
     * shelved offloaded.
     * SHELVED_OFFLOADED: The shelved server is offloaded (removed from the compute host) and it needs unshelved action
     * to be used again.
     * SHUTOFF. The server is powered off and the disk image still persists.
     * SOFT_DELETED. The server is marked as deleted but the disk images are still available to restore.
     * SUSPENDED. The server is suspended, either by request or necessity. This status appears for only the XenServer/XCP,
     * KVM, and ESXi hypervisors. Administrative users can suspend an instance if it is infrequently used or
     * to perform system maintenance. When you suspend an instance, its VM state is stored on disk, all memory
     * is written to disk, and the virtual machine is stopped. Suspending an instance is similar to placing
     * a device in hibernation; memory and vCPUs become available to create other instances.
     * UNKNOWN. The state of the server is unknown. Contact your cloud provider.
     * VERIFY_RESIZE. System is awaiting confirmation that the server is operational after a move or resize.
     * <p>
     * There is whitelist for valid filter keys. Any filter key other than from whitelist will be silently ignored.
     * <p>
     * For non-admin users, whitelist is different from admin users whitelist. Valid whitelist for non-admin users includes:
     * all_tenants
     * changes-since
     * flavor
     * image
     * ip
     * ip6 (New in version 2.5)
     * name
     * not-tags (New in version 2.26)
     * not-tags-any (New in version 2.26)
     * reservation_id
     * status
     * tags (New in version 2.26)
     * tags-any (New in version 2.26)
     * changes-before (New in version 2.66)
     * For admin user, whitelist includes all filter keys mentioned in Request Section.
     * <p>
     * Normal response codes: 200
     * Error response codes: badRequest(400), unauthorized(401), forbidden(403)
     * <p>
     * Reference URL: https://developer.openstack.org/api-ref/compute/#servers-run-an-action-servers-action
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
     * @return A map with strings as keys and strings as values that contains: outcome of the action (or failure message
     * and the exception if there is one), returnCode of the operation
     */
    @Action(name = "List Servers",
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
                                       @Param(value = ACCESS_IP_V4) String accessIpV4,
                                       @Param(value = ACCESS_IP_V6) String accessIpV6,
                                       @Param(value = ALL_TENANTS) String allTenants,
                                       @Param(value = AUTO_DISK_CONFIG) String autoDiskConfig,
                                       @Param(value = AVAILABILITY_ZONE) String availabilityZone,
                                       @Param(value = CHANGES_SINCE) String changesSince,
                                       @Param(value = CONFIG_DRIVE) String configDrive,
                                       @Param(value = CREATED_AT) String createdAt,
                                       @Param(value = DELETED) String deleted,
                                       @Param(value = DESCRIPTION) String description,
                                       @Param(value = FLAVOR) String flavor,
                                       @Param(value = HOST) String host,
                                       @Param(value = HOSTNAME) String hostname,
                                       @Param(value = IMAGE) String image,
                                       @Param(value = IP) String ip,
                                       @Param(value = IP6) String ip6,
                                       @Param(value = KERNEL_ID) String kernelId,
                                       @Param(value = KEY_NAME) String keyName,
                                       @Param(value = LAUNCH_INDEX) String launchIndex,
                                       @Param(value = LAUNCHED_AT) String launchedAt,
                                       @Param(value = LIMIT) String limit,
                                       @Param(value = LOCKED_BY) String lockedBy,
                                       @Param(value = MARKER) String marker,
                                       @Param(value = NAME) String name,
                                       @Param(value = NODE) String node,
                                       @Param(value = POWER_STATE) String powerState,
                                       @Param(value = PROGRESS) String progress,
                                       @Param(value = PROJECT_ID) String projectId,
                                       @Param(value = RAMDISK_ID) String ramdiskId,
                                       @Param(value = RESERVATION_ID) String reservationId,
                                       @Param(value = ROOT_DEVICE_NAME) String rootDeviceName,
                                       @Param(value = SOFT_DELETED) String softDeleted,
                                       @Param(value = SORT_DIR) String sortDir,
                                       @Param(value = SORT_KEY) String sortKey,
                                       @Param(value = STATUS) String status,
                                       @Param(value = TASK_STATE) String taskState,
                                       @Param(value = TERMINATED_AT) String terminatedAt,
                                       @Param(value = USER_ID) String userId,
                                       @Param(value = UUID) String uuid,
                                       @Param(value = VM_STATE) String vmState,
                                       @Param(value = NOT_TAGS) String notTags,
                                       @Param(value = NOT_TAGS_ANY) String notTagsAny,
                                       @Param(value = TAGS) String tags,
                                       @Param(value = TAGS_ANY) String tagsAny,
                                       @Param(value = CHANGES_BEFORE) String changesBefore) {
        try {
            HttpClientInputs httpClientInputs = buildHttpClientInputs(proxyHost, proxyPort, proxyUsername, proxyPassword,
                    trustAllRoots, x509HostnameVerifier, trustKeystore, trustPassword, keystore, keystorePassword,
                    connectTimeout, socketTimeout, useCookies, keepAlive, METHOD_NAME);

            final CommonInputsBuilder commonInputsBuilder = new CommonInputsBuilder.Builder()
                    .withEndpoint(endpoint)
                    .withAction(LIST_ALL_MAJOR_VERSIONS)
                    .withApi(API)
                    .withVersion(defaultIfEmpty(version, DEFAULT_COMPUTE_VERSION))
                    .build();

            return new OpenstackService().execute(httpClientInputs, commonInputsBuilder);
        } catch (OpenstackException | MalformedURLException exception) {
            return getFailureResultsMap(exception);
        }
    }
}
