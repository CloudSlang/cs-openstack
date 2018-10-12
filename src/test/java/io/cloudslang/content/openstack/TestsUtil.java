package io.cloudslang.content.openstack;

import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ServersInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;
import org.junit.rules.ExpectedException;

public class TestsUtil {
    private TestsUtil() {
    }

    public static void setExpectedExceptions(Class<?> type, ExpectedException exception, String message) {
        //noinspection unchecked
        exception.expect((Class<? extends Throwable>) type);
        exception.expectMessage(message);
    }

    public static InputsWrapper getInputsWrapper(String endpoint, String api, String version, String action) {
        return new InputsWrapper.Builder()
                .withCommonInputs(getCommonInputs(endpoint, api, version, action))
                .build();
    }

    public static CommonInputsBuilder getCommonInputs(String endpoint, String api, String version, String action) {
        return new CommonInputsBuilder.Builder()
                .withEndpoint(endpoint)
                .withApi(api)
                .withVersion(version)
                .withAction(action)
                .build();
    }

    public static ApiInputsBuilder getApiInputs(String version) {
        return new ApiInputsBuilder.Builder()
                .withApiVersion(version)
                .build();
    }

    public static IdentityInputsBuilder getIdentityInputs(String input) {
        return new IdentityInputsBuilder.Builder()
                .withNoCatalog(input)
                .build();
    }

    public static ServersInputsBuilder getServerInputs(String accessIpV4, String accessIpV6, String allTenants, String autoDiskConfig,
                                                       String availabilityZone, String changesBefore, String changesSince,
                                                       String configDrive, String createdAt, String deleted, String description,
                                                       String flavor, String host, String hostname, String image, String ip,
                                                       String ip6, String kernelId, String keyName, String launchIndex,
                                                       String launchedAt, String limit, String lockedBy, String marker,
                                                       String name, String node, String powerState, String progress,
                                                       String projectId, String ramdiskId, String reservationId,
                                                       String rootDeviceName, String softDeleted, String sortDir,
                                                       String sortKey, String status, String taskState, String terminatedAt,
                                                       String userId, String uuid, String vmState, String notTags,
                                                       String notTagsAny, String tags, String tagsAny) throws OpenstackException {
        return new ServersInputsBuilder.Builder()
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
    }
}
