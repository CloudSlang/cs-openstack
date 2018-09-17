package io.cloudslang.content.openstack.compute.entities.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ListAllMajorVersionsResponse {
    // A list of version objects that describe the API versions available.
    private List<Version> versions;

    // A common name for the version in question. Informative only, it has no real semantic meaning.
    private String id;

    // Links to the resources in question. See https://developer.openstack.org/api-guide/compute/links_and_references.html
    // for more info.
    private List<Link> links;

    // If this version of the API supports microversions, the minimum microversion that is supported.
    // This will be the empty string if microversions are not supported.
    @SerializedName("min_version")
    private String minVersion;

    // The status of this API version. This can be one of:
    //
    //CURRENT: this is the preferred version of the API to use
    //SUPPORTED: this is an older, but still supported version of the API
    //DEPRECATED: a deprecated version of the API that is slated for removal
    private String status;

    // This is a fixed string. It is 2011-01-21T11:33:21Z in version 2.0, 2013-07-23T11:33:21Z in version 2.1.
    private String updated;

    // If this version of the API supports microversions, the maximum microversion that is supported.
    // This will be the empty string if microversions are not supported.
    private String version;

    public ListAllMajorVersionsResponse(List<Version> versions, String id, List<Link> links, String minVersion, String status, String updated, String version) {
        this.versions = versions;
        this.id = id;
        this.links = links;
        this.minVersion = minVersion;
        this.status = status;
        this.updated = updated;
        this.version = version;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListAllMajorVersionsResponse that = (ListAllMajorVersionsResponse) o;
        return Objects.equals(versions, that.versions) &&
                Objects.equals(id, that.id) &&
                Objects.equals(links, that.links) &&
                Objects.equals(minVersion, that.minVersion) &&
                Objects.equals(status, that.status) &&
                Objects.equals(updated, that.updated) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versions, id, links, minVersion, status, updated, version);
    }

    @Override
    public String toString() {
        return "ListAllMajorVersionsResponse{" +
                "id='" + id + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", status='" + status + '\'' +
                ", updated='" + updated + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
