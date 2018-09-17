package io.cloudslang.content.openstack.compute.entities.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Version {
    private String id;
    private List<Link> links;
    private String status;
    private String version;

    @SerializedName("min_version")
    private String minVersion;

    private String updated;

    public Version(String id, List<Link> links, String status, String version, String minVersion, String updated) {
        this.id = id;
        this.links = links;
        this.status = status;
        this.version = version;
        this.minVersion = minVersion;
        this.updated = updated;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version1 = (Version) o;
        return Objects.equals(id, version1.id) &&
                Objects.equals(links, version1.links) &&
                Objects.equals(status, version1.status) &&
                Objects.equals(version, version1.version) &&
                Objects.equals(minVersion, version1.minVersion) &&
                Objects.equals(updated, version1.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, links, status, version, minVersion, updated);
    }

    @Override
    public String toString() {
        return "Version{" +
                "id='" + id + '\'' +
                ", links=" + links +
                ", status='" + status + '\'' +
                ", version='" + version + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }
}
