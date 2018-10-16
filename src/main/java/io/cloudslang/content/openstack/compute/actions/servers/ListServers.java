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
import io.cloudslang.content.openstack.compute.builders.ServersInputsBuilder;
import io.cloudslang.content.openstack.compute.responses.servers.ListServersResponse;
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
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.SERVERS;
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
import static io.cloudslang.content.openstack.entities.Constants.Headers.TOKEN;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.ENDPOINT;
import static io.cloudslang.content.openstack.entities.Inputs.CommonInputs.VERSION;
import static io.cloudslang.content.openstack.handlers.ResponseHandler.handleResponse;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.http.client.methods.HttpGet.METHOD_NAME;

public class ListServers {
    private static final String AVAILABLE_SERVERS = "availableServers";

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
     * @param accessIpV4           Optional - Filter server list result by IPv4 address that should be used to access the
     *                             server.
     * @param accessIpV6           Optional - Filter server list result by IPv6 address that should be used to access the
     *                             server.
     * @param allTenants           Optional - Specify the all_tenants query parameter to list all instances for all projects.
     *                             By default this is only allowed by administrators. If the value of this parameter is
     *                             not specified, it is treated as True. If the value is specified, "1", "t", "true",
     *                             "on", "y" and "yes" are treated as True. "0", "f", "false", "off", "n" and "no" are
     *                             treated as False. (They are case-insensitive.)
     *                             Valid values: "",  "1", "t", "true", "on", "y", "yes", "0", "f", "false", "off", "n",
     *                             "no"
     * @param autoDiskConfig       Optional - Filter the server list result by the disk_config setting of the server.
     *                             This parameter is only valid when specified by administrators. If non-admin users
     *                             specify this parameter, it is ignored.
     *                             Valid values: "auto", "manual"
     *                             Default value: "auto"
     * @param availabilityZone     Optional - Filter the server list result by server availability zone. This parameter
     *                             is only valid when specified by administrators. If non-admin users specify this parameter,
     *                             it is ignored.
     * @param changesBefore        Optional - Filters the response by a date and time stamp when the server last changed.
     *                             Those servers that changed before or equal to the specified date and time stamp are
     *                             returned. To help keep track of changes this may also return recently deleted servers.
     *                             The date and time stamp format is ISO 8601. The UTC time zone is reference.
     *                             Note: New in version 2.66
     *                             Example: CCYY-MM-DDThh:mm:ss
     *                             The ±hh:mm value, if included, returns the time zone as an offset from UTC.
     *                             Example: 2015-08-27T09:49:58
     * @param changesSince         Optional - Filters the response by a date and time stamp when the server last changed
     *                             status. To help keep track of changes this may also return recently deleted servers.
     *                             The date and time stamp format is ISO 8601. The UTC time zone is reference.
     *                             Example: CCYY-MM-DDThh:mm:ss
     *                             Example: 2015-08-27T09:49:58
     * @param configDrive          Optional - Filter the server list result by the config drive setting of the server.
     *                             This parameter is only valid when specified by administrators. If non-admin users
     *                             specify this parameter, it is ignored.
     * @param createdAt            Optional - Filter the server list result by a date and time stamp when server was created.
     *                             This parameter is only valid when specified by administrators. If non-admin users
     *                             specify this parameter, it is ignored.
     *                             The date and time stamp format is ISO 8601.
     *                             Example: CCYY-MM-DDThh:mm:ss±hh:mm
     *                             The ±hh:mm value, if included, returns the time zone as an offset from UTC.
     *                             Example: 2015-08-27T09:49:58-05:00.
     *                             If you omit the time zone, the UTC time zone is assumed.
     * @param deleted              Optional - Show deleted items only. In some circumstances deleted items will still be
     *                             accessible via the backend database, however there is no contract on how long, so this
     *                             parameter should be used with caution. This parameter is only valid when specified by
     *                             administrators. If non-admin users specify this parameter, it is ignored.
     *                             Valid values: "1", "t", "true", "on", "y", "yes" are treated as True (case-insensitive).
     *                             Other than them are treated as False.
     * @param description          Optional - Filter the server list result by description.
     *                             This parameter is only valid when specified by administrators. If non-admin users specify
     *                             this parameter, it is ignored.
     *                             Note: display_description can also be requested which is alias of description but that
     *                             is not recommended to use as that will be removed in future.
     * @param flavor               Optional - Filters the response by a flavor, as a UUID. A flavor is a combination of
     *                             memory, disk size, and CPUs.
     * @param host                 Optional - Filter the server list result by the host name of compute node.
     *                             This parameter is only valid when specified by administrators. If non-admin users specify
     *                             this parameter, it is ignored.
     * @param hostname             Optional - Filter the server list result by the host name of server. This parameter is
     *                             only valid when specified by administrators. If non-admin users specify this parameter,
     *                             it is ignored.
     * @param image                Optional - Filters the response by an image, as a UUID.
     *                             Note: image_ref’ can also be requested which is alias of ‘image’ but that is not
     *                             recommended to use as that will be removed in future.
     * @param ip                   Optional - An IPv4 address to filter results by.
     * @param ip6                  Optional - An IPv6 address to filter results by. Up to microversion 2.4, this parameter
     *                             is only valid when specified by administrators. If non-admin users specify this parameter,
     *                             it is ignored. Starting from microversion 2.5, this parameter is valid for no-admin users
     *                             as well as administrators.
     * @param kernelId             Optional - Filter the server list result by the UUID of the kernel image when using
     *                             an AMI. This parameter is only valid when specified by administrators. If non-admin
     *                             users specify this parameter, it is ignored.
     * @param keyName              Optional - Filter the server list result by keypair name.
     *                             This parameter is only valid when specified by administrators. If non-admin users specify
     *                             this parameter, it is ignored.
     * @param launchIndex          Optional - Filter the server list result by the sequence in which the servers were
     *                             launched. This parameter is only valid when specified by administrators. If non-admin
     *                             users specify this parameter, it is ignored.
     * @param launchedAt           Optional - Filter the server list result by a date and time stamp when the instance
     *                             was launched. The date and time stamp format is ISO 8601.
     *                             This parameter is only valid when specified by administrators.
     *                             If non-admin users specify this parameter, it is ignored.
     *                             Example: CCYY-MM-DDThh:mm:ss±hh:mm
     *                             The ±hh:mm value, if included, returns the time zone as an offset from UTC.
     *                             Example: 2015-08-27T09:49:58-05:00.
     *                             If you omit the time zone, the UTC time zone is assumed.
     * @param limit                Optional - Requests a page size of items. Returns a number of items up to a limit value.
     *                             Use the limit parameter to make an initial limited request and use the ID of the last-seen
     *                             item from the response as the marker parameter value in a subsequent limited request.
     * @param lockedBy             Optional - Filter the server list result by who locked the server, possible value could
     *                             be admin or owner. This parameter is only valid when specified by administrators.
     *                             If non-admin users specify this parameter, it is ignored.
     * @param marker               Optional - The ID of the last-seen item. Use the limit parameter to make an initial
     *                             limited request and use the ID of the last-seen item from the response as the marker
     *                             parameter value in a subsequent limited request.
     * @param name                 Optional - Filters the response by a server name, as a string. You can use regular
     *                             expressions in the query. For example, the ?name=bob regular expression returns both
     *                             bob and bobb. If you must match on only bob, you can use a regular expression that
     *                             matches the syntax of the underlying database server that is implemented for Compute,
     *                             such as MySQL or PostgreSQL.
     *                             Note: ‘display_name’ can also be requested which is alias of ‘name’ but that is not
     *                             recommended to use as that will be removed in future.
     * @param node                 Optional - Filter the server list result by the node. This parameter is only valid when
     *                             specified by administrators. If non-admin users specify this parameter, it is ignored.
     * @param powerState           Optional - Filter the server list result by server power state.
     *                             Possible values are integer values that is mapped as:
     *                             0: NOSTATE
     *                             1: RUNNING
     *                             3: PAUSED
     *                             4: SHUTDOWN
     *                             6: CRASHED
     *                             7: SUSPENDED
     *                             This parameter is only valid when specified by administrators. If non-admin users specify
     *                             this parameter, it is ignored.
     * @param progress             Optional - Filter the server list result by the progress of the server. The value could
     *                             be from 0 to 100 as integer. This parameter is only valid when specified by administrators.
     *                             If non-admin users specify this parameter, it is ignored.
     * @param projectId            Optional - Filter the list of servers by the given project ID. This filter only works
     *                             when the all_tenants filter is also specified.
     *                             Note: ‘tenant_id’ can also be requested which is alias of ‘project_id’ but that is not
     *                             recommended to use as that will be removed in future.
     * @param ramdiskId            Optional - Filter the server list result by the UUID of the ramdisk image when using
     *                             an AMI. This parameter is only valid when specified by administrators. If non-admin
     *                             users specify this parameter, it is ignored.
     * @param reservationId        Optional - A reservation id as returned by a servers multiple create call.
     * @param rootDeviceName       Optional - Filter the server list result by the root device name of the server.
     *                             This parameter is only valid when specified by administrators. If non-admin users
     *                             specify this parameter, it is ignored.
     * @param softDeleted          Optional - Filter the server list by SOFT_DELETED status. This parameter is only valid
     *                             when the deleted=True filter parameter is specified.
     * @param sortDir              Optional - Sort direction. You can specify multiple pairs of sort key and sort direction
     *                             query parameters. If you omit the sort direction in a pair, the API uses the natural
     *                             sorting direction of the direction of the server sort_key attribute.
     *                             Valid values: "asc" (ascending), "desc" (descending).
     *                             Default value: "desc"
     * @param sortKey              Optional - Sorts by a server attribute. Default attribute is created_at. You can specify
     *                             multiple pairs of sort key and sort direction query parameters. If you omit the sort
     *                             direction in a pair, the API uses the natural sorting direction of the server sort_key
     *                             attribute. The sort keys are limited to: "access_ip_v4", "access_ip_v6", "auto_disk_config",
     *                             "availability_zone", "config_drive", "created_at", "display_description", "display_name",
     *                             "host", "hostname", "image_ref", "instance_type_id", "kernel_id", "key_name", "launch_index",
     *                             "launched_at", "locked_by", "node", "power_state", "progress", "project_id", "ramdisk_id",
     *                             "root_device_name", "task_state", "terminated_at", "updated_at", "user_id", "uuid" and
     *                             "vm_state".
     *                             Note: host and node are only allowed for admin. If non-admin users specify them, a
     *                             403 error is returned.
     * @param status               Optional - Filters the response by a server status, as a string.
     *                             Example: ACTIVE.
     *                             Note: Up to microversion 2.37, an empty list is returned if an invalid status is specified.
     *                             Starting from microversion 2.38, a 400 error is returned in that case.
     * @param taskState            Optional - Filter the server list result by task state. This parameter is only valid
     *                             when specified by administrators. If non-admin users specify this parameter, it is ignored.
     * @param terminatedAt         Optional - Filter the server list result by a date and time stamp when instance was
     *                             terminated. The date and time stamp format is ISO 8601.
     *                             Example: CCYY-MM-DDThh:mm:ss±hh:mm
     *                             The ±hh:mm value, if included, returns the time zone as an offset from UTC.
     *                             Example: 2015-08-27T09:49:58-05:00.
     *                             If you omit the time zone, the UTC time zone is assumed.
     *                             This parameter is only valid when specified by administrators. If non-admin users specify
     *                             this parameter, it is ignored.
     * @param userId               Optional - Filter the list of servers by the given user ID. This parameter is only
     *                             valid when specified by administrators. If non-admin users specify this parameter, it
     *                             is ignored.
     * @param uuid                 Optional - Filter the server list result by the UUID of the server. This parameter is
     *                             only valid when specified by administrators. If non-admin users specify this parameter,
     *                             it is ignored.
     * @param vmState              Optional - Filter the server list result by vm state. This parameter is only valid when
     *                             specified by administrators. If non-admin users specify this parameter, it is ignored.
     *                             Valid values: "ACTIVE", "BUILDING", "DELETED", "ERROR", "PAUSED", "RESCUED", "RESIZED",
     *                             "SHELVED", "SHELVED_OFFLOADED", "SOFT_DELETED", "STOPPED", "SUSPENDED"
     * @param notTags              Optional - A list of tags to filter the server list by. Servers that don’t match all
     *                             tags in this list will be returned. Boolean expression in this case is ‘NOT (t1 AND t2)’.
     *                             Tags in query must be separated by comma.
     *                             Note: New in version 2.26
     * @param notTagsAny           Optional - A list of tags to filter the server list by. Servers that match any tag in
     *                             this list will be returned. Boolean expression in this case is ‘t1 OR t2’. Tags in
     *                             query must be separated by comma.
     *                             Note: New in version 2.26
     * @param tags                 Optional - A list of tags to filter the server list by. Servers that match all tags in
     *                             this list will be returned. Boolean expression in this case is ‘t1 AND t2’. Tags in
     *                             query must be separated by comma.
     *                             Note: New in version 2.26
     * @param tagsAny              Optional - A list of tags to filter the server list by. Servers that match any tag in
     *                             this list will be returned. Boolean expression in this case is ‘t1 OR t2’. Tags in
     *                             query must be separated by comma.
     *                             Note: New in version 2.26
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
                                       @Param(value = TOKEN) String token,
                                       @Param(value = ACCESS_IP_V4) String accessIpV4,
                                       @Param(value = ACCESS_IP_V6) String accessIpV6,
                                       @Param(value = ALL_TENANTS) String allTenants,
                                       @Param(value = AUTO_DISK_CONFIG) String autoDiskConfig,
                                       @Param(value = AVAILABILITY_ZONE) String availabilityZone,
                                       @Param(value = CHANGES_BEFORE) String changesBefore,
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
                                       @Param(value = TAGS_ANY) String tagsAny) {
        try {
            HttpClientInputs httpClientInputs = buildHttpClientInputs(proxyHost, proxyPort, proxyUsername, proxyPassword,
                    trustAllRoots, x509HostnameVerifier, trustKeystore, trustPassword, keystore, keystorePassword,
                    connectTimeout, socketTimeout, useCookies, keepAlive, METHOD_NAME);

            final CommonInputsBuilder commonInputsBuilder = new CommonInputsBuilder.Builder()
                    .withEndpoint(endpoint)
                    .withAction(LIST_SERVERS)
                    .withApi(SERVERS)
                    .withVersion(defaultIfEmpty(version, DEFAULT_COMPUTE_VERSION))
                    .withToken(token)
                    .build();

            final ServersInputsBuilder serversInputsBuilder = new ServersInputsBuilder.Builder()
                    .withAccessIpV4(accessIpV4)
                    .withAccessIpV6(accessIpV6)
                    .withAllTenants(allTenants)
                    .withAutoDiskConfig(autoDiskConfig)
                    .withAvailabilityZone(availabilityZone)
                    .withChangesBefore(changesBefore)
                    .withChangesSince(changesSince)
                    .withConfigDrive(configDrive)
                    .withCreatedAt(createdAt)
                    .withDeleted(deleted)
                    .withDescription(description)
                    .withFlavor(flavor)
                    .withHost(host)
                    .withHostname(hostname)
                    .withImage(image)
                    .withIp(ip)
                    .withIp6(ip6)
                    .withKernelId(kernelId)
                    .withKeyName(keyName)
                    .withLaunchIndex(launchIndex)
                    .withLaunchedAt(launchedAt)
                    .withLimit(limit)
                    .withLockedBy(lockedBy)
                    .withMarker(marker)
                    .withName(name)
                    .withNode(node)
                    .withPowerState(powerState)
                    .withProgress(progress)
                    .withProjectId(projectId)
                    .withRamdiskId(ramdiskId)
                    .withReservationId(reservationId)
                    .withRootDeviceName(rootDeviceName)
                    .withSoftDeleted(softDeleted)
                    .withSortDir(sortDir)
                    .withSortKey(sortKey)
                    .withStatus(status)
                    .withTaskState(taskState)
                    .withTerminatedAt(terminatedAt)
                    .withUserId(userId)
                    .withUuid(uuid)
                    .withVmState(vmState)
                    .withNotTags(notTags)
                    .withNotTagsAny(notTagsAny)
                    .withTags(tags)
                    .withTagsAny(tagsAny)
                    .build();

            Map<String, String> response = new OpenstackService().execute(httpClientInputs, commonInputsBuilder, serversInputsBuilder);

            String additionalInformation = handleResponse(response.get(RETURN_RESULT), ListServersResponse.class);
            if (isNotBlank(additionalInformation)) {
                response.put(AVAILABLE_SERVERS, additionalInformation);
            }

            return response;
        } catch (OpenstackException | MalformedURLException exception) {
            return getFailureResultsMap(exception);
        }
    }
}
