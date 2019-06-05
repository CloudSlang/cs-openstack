package io.cloudslang.content.openstack.compute.builders;

import io.cloudslang.content.openstack.compute.entities.servers.PowerState;
import io.cloudslang.content.openstack.compute.entities.servers.SortKey;
import io.cloudslang.content.openstack.compute.entities.servers.Status;
import io.cloudslang.content.openstack.compute.entities.servers.VmState;
import io.cloudslang.content.openstack.exceptions.OpenstackException;

import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.THRESHOLD_VERSION_FOR_TIMESTAMP_FILTERING_SERVERS;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getValidISO8601StringFormat;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getValidInt;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getValidIntWithinRange;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getValidStringInput;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getValidUuidFormattedString;
import static io.cloudslang.content.openstack.compute.validators.Validators.isIpV4;
import static io.cloudslang.content.openstack.compute.validators.Validators.isIpV6;
import static io.cloudslang.content.openstack.compute.validators.Validators.shouldBeTrue;
import static io.cloudslang.content.openstack.validators.Validators.isInputGreaterOrEqualThanThreshold;
import static io.cloudslang.content.openstack.validators.Validators.isValidHost;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ServersInputsBuilder {
    private final String accessIpV4;
    private final String accessIpV6;
    private final String autoDiskConfig;
    private final String availabilityZone;
    private final String changesSince;
    private final String configDrive;
    private final String createdAt;
    private final String description;
    private final String flavor;
    private final String host;
    private final String hostname;
    private final String image;
    private final String ip;
    private final String ip6;
    private final String kernelId;
    private final String keyName;
    private final String launchedAt;
    private final String lockedBy;
    private final String marker;
    private final String name;
    private final String node;
    private final String projectId;
    private final String ramdiskId;
    private final String reservationId;
    private final String rootDeviceName;
    private final String serverId;
    private final String softDeleted;
    private final String sortDir;
    private final String sortKey;
    private final String status;
    private final String taskState;
    private final String terminatedAt;
    private final String userId;
    private final String uuid;
    private final String vmState;
    private final String notTags;
    private final String notTagsAny;
    private final String tags;
    private final String tagsAny;
    private final String changesBefore;

    private final Integer launchIndex;
    private final Integer limit;
    private final Integer powerState;
    private final Integer progress;

    private final Boolean deleted;
    private final Boolean allTenants;

    private ServersInputsBuilder(Builder builder) {
        this.accessIpV4 = builder.accessIpV4;
        this.accessIpV6 = builder.accessIpV6;
        this.autoDiskConfig = builder.autoDiskConfig;
        this.availabilityZone = builder.availabilityZone;
        this.changesSince = builder.changesSince;
        this.configDrive = builder.configDrive;
        this.createdAt = builder.createdAt;
        this.description = builder.description;
        this.flavor = builder.flavor;
        this.host = builder.host;
        this.hostname = builder.hostname;
        this.image = builder.image;
        this.ip = builder.ip;
        this.ip6 = builder.ip6;
        this.kernelId = builder.kernelId;
        this.keyName = builder.keyName;
        this.launchedAt = builder.launchedAt;
        this.lockedBy = builder.lockedBy;
        this.marker = builder.marker;
        this.name = builder.name;
        this.node = builder.node;
        this.projectId = builder.projectId;
        this.ramdiskId = builder.ramdiskId;
        this.reservationId = builder.reservationId;
        this.rootDeviceName = builder.rootDeviceName;
        this.serverId = builder.serverId;
        this.softDeleted = builder.softDeleted;
        this.sortDir = builder.sortDir;
        this.sortKey = builder.sortKey;
        this.status = builder.status;
        this.taskState = builder.taskState;
        this.terminatedAt = builder.terminatedAt;
        this.userId = builder.userId;
        this.uuid = builder.uuid;
        this.vmState = builder.vmState;
        this.notTags = builder.notTags;
        this.notTagsAny = builder.notTagsAny;
        this.tags = builder.tags;
        this.tagsAny = builder.tagsAny;
        this.changesBefore = builder.changesBefore;

        this.launchIndex = builder.launchIndex;
        this.limit = builder.limit;
        this.powerState = builder.powerState;
        this.progress = builder.progress;

        this.allTenants = builder.allTenants;
        this.deleted = builder.deleted;
    }

    public String getAccessIpV4() {
        return accessIpV4;
    }

    public String getAccessIpV6() {
        return accessIpV6;
    }

    public String getAutoDiskConfig() {
        return autoDiskConfig;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public String getChangesSince() {
        return changesSince;
    }

    public String getConfigDrive() {
        return configDrive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getHost() {
        return host;
    }

    public String getHostname() {
        return hostname;
    }

    public String getImage() {
        return image;
    }

    public String getIp() {
        return ip;
    }

    public String getIp6() {
        return ip6;
    }

    public String getKernelId() {
        return kernelId;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getLaunchedAt() {
        return launchedAt;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public String getMarker() {
        return marker;
    }

    public String getName() {
        return name;
    }

    public String getNode() {
        return node;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getRamdiskId() {
        return ramdiskId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRootDeviceName() {
        return rootDeviceName;
    }

    public String getServerId() {
        return serverId;
    }

    public String getSoftDeleted() {
        return softDeleted;
    }

    public String getSortDir() {
        return sortDir;
    }

    public String getSortKey() {
        return sortKey;
    }

    public String getStatus() {
        return status;
    }

    public String getTaskState() {
        return taskState;
    }

    public String getTerminatedAt() {
        return terminatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVmState() {
        return vmState;
    }

    public String getNotTags() {
        return notTags;
    }

    public String getNotTagsAny() {
        return notTagsAny;
    }

    public String getTags() {
        return tags;
    }

    public String getTagsAny() {
        return tagsAny;
    }

    public Integer getLaunchIndex() {
        return launchIndex;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPowerState() {
        return powerState;
    }

    public String getChangesBefore() {
        return changesBefore;
    }

    public Boolean getAllTenants() {
        return allTenants;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Integer getProgress() {
        return progress;
    }

    public static class Builder {
        private String accessIpV4;
        private String accessIpV6;
        private String autoDiskConfig;
        private String availabilityZone;
        private String changesSince;
        private String configDrive;
        private String createdAt;
        private String description;
        private String flavor;
        private String host;
        private String hostname;
        private String image;
        private String ip;
        private String ip6;
        private String kernelId;
        private String keyName;
        private String launchedAt;
        private String lockedBy;
        private String marker;
        private String name;
        private String node;
        private String projectId;
        private String ramdiskId;
        private String reservationId;
        private String rootDeviceName;
        private String serverId;
        private String softDeleted;
        private String sortDir;
        private String sortKey;
        private String status;
        private String taskState;
        private String terminatedAt;
        private String userId;
        private String uuid;
        private String vmState;
        private String notTags;
        private String notTagsAny;
        private String tags;
        private String tagsAny;
        private String changesBefore;

        private Integer launchIndex;
        private Integer limit;
        private Integer powerState;
        private Integer progress;

        private Boolean deleted;
        private Boolean allTenants;

        public ServersInputsBuilder build() {
            return new ServersInputsBuilder(this);
        }

        public Builder withAccessIpV4(String input) {
            accessIpV4 = input;
            return this;
        }

        public Builder withAccessIpV6(String input) {
            accessIpV6 = input;
            return this;
        }

        public Builder withAutoDiskConfig(String input) {
            autoDiskConfig = input;
            return this;
        }

        public Builder withAvailabilityZone(String input) {
            availabilityZone = input;
            return this;
        }

        public Builder withChangesBefore(String input) throws OpenstackException {
            changesBefore = isInputGreaterOrEqualThanThreshold(input, THRESHOLD_VERSION_FOR_TIMESTAMP_FILTERING_SERVERS) ?
                    getValidISO8601StringFormat(input) : null;
            return this;
        }

        public Builder withChangesSince(String input) throws OpenstackException {
            changesSince = getValidISO8601StringFormat(input);
            return this;
        }

        public Builder withConfigDrive(String input) {
            configDrive = input;
            return this;
        }

        public Builder withCreatedAt(String input) throws OpenstackException {
            createdAt = getValidISO8601StringFormat(input);
            return this;
        }

        public Builder withDescription(String input) {
            description = input;
            return this;
        }

        public Builder withFlavor(String input) {
            flavor = input;
            return this;
        }

        public Builder withHost(String input) throws OpenstackException {
            host = getValidStringInput(input, isValidHost(input), "Specify a valid host value.");
            return this;
        }

        public Builder withHostname(String input) {
            hostname = input;
            return this;
        }

        public Builder withImage(String input) {
            image = input;
            return this;
        }

        public Builder withIp(String input) throws OpenstackException {
            ip = getValidStringInput(input, isIpV4(input), "Specify a valid IPv4 address value.");
            return this;
        }

        public Builder withIp6(String input) throws OpenstackException {
            ip6 = getValidStringInput(input, isIpV6(input), "Specify a valid IPv6 address value.");
            return this;
        }

        public Builder withKernelId(String input) {
            kernelId = input;
            return this;
        }

        public Builder withKeyName(String input) {
            keyName = input;
            return this;
        }

        public Builder withLaunchedAt(String input) throws OpenstackException {
            launchedAt = getValidISO8601StringFormat(input);
            return this;
        }

        public Builder withLockedBy(String input) {
            lockedBy = input;
            return this;
        }

        public Builder withMarker(String input) {
            marker = input;
            return this;
        }

        public Builder withName(String input) {
            name = input;
            return this;
        }

        public Builder withNode(String input) {
            node = input;
            return this;
        }

        public Builder withProjectId(String input) {
            projectId = input;
            return this;
        }

        public Builder withRamdiskId(String input) {
            ramdiskId = input;
            return this;
        }

        public Builder withReservationId(String input) {
            reservationId = input;
            return this;
        }

        public Builder withRootDeviceName(String input) {
            rootDeviceName = input;
            return this;
        }

        public Builder withServerId(String input) throws OpenstackException {
            serverId = getValidUuidFormattedString(input);
            return this;
        }

        public Builder withSoftDeleted(String input) {
            softDeleted = input;
            return this;
        }

        public Builder withSortDir(String input) {
            sortDir = input;
            return this;
        }

        public Builder withSortKey(String input) throws OpenstackException {
            sortKey = SortKey.fromString(input);
            return this;
        }

        public Builder withStatus(String input) throws OpenstackException {
            status = Status.fromString(input);
            return this;
        }

        public Builder withTaskState(String input) {
            taskState = input;
            return this;
        }

        public Builder withTerminatedAt(String input) throws OpenstackException {
            terminatedAt = getValidISO8601StringFormat(input);
            return this;
        }

        public Builder withUserId(String input) {
            userId = input;
            return this;
        }

        public Builder withUuid(String input) {
            uuid = input;
            return this;
        }

        public Builder withVmState(String input) throws OpenstackException {
            vmState = VmState.fromString(input);
            return this;
        }

        public Builder withNotTags(String input) {
            notTags = input;
            return this;
        }

        public Builder withNotTagsAny(String input) {
            notTagsAny = input;
            return this;
        }

        public Builder withTags(String input) {
            tags = input;
            return this;
        }

        public Builder withTagsAny(String input) {
            tagsAny = input;
            return this;
        }

        public Builder withLaunchIndex(String input) throws OpenstackException {
            launchIndex = getValidInt(input, 1);
            return this;
        }

        public Builder withLimit(String input) throws OpenstackException {
            limit = getValidInt(input, 20);
            return this;
        }

        public Builder withPowerState(String input) throws OpenstackException {
            powerState = PowerState.fromString(input);
            return this;
        }

        public Builder withProgress(String input) throws OpenstackException {
            progress = getValidIntWithinRange(input, 0, 100, 100);
            return this;
        }

        public Builder withAllTenants(String input) {
            allTenants = isBlank(input) || shouldBeTrue(input);
            return this;
        }

        public Builder withDeleted(String input) {
            deleted = isNotBlank(input) && shouldBeTrue(input);
            return this;
        }
    }
}
