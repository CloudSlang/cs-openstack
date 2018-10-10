package io.cloudslang.content.openstack.compute.responses.servers;

import com.google.gson.annotations.SerializedName;
import io.cloudslang.content.openstack.compute.entities.Link;
import io.cloudslang.content.openstack.compute.entities.servers.Servers;

import java.util.List;
import java.util.Objects;

public class ListServersResponse {
    private List<Servers> servers;

    @SerializedName("servers_links")
    private List<Link> serversLinks;

    public ListServersResponse(List<Servers> servers, List<Link> serversLinks) {
        this.servers = servers;
        this.serversLinks = serversLinks;
    }

    public List<Servers> getServers() {
        return servers;
    }

    public void setServers(List<Servers> servers) {
        this.servers = servers;
    }

    public List<Link> getServersLinks() {
        return serversLinks;
    }

    public void setServersLinks(List<Link> serversLinks) {
        this.serversLinks = serversLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListServersResponse that = (ListServersResponse) o;
        return Objects.equals(servers, that.servers) &&
                Objects.equals(serversLinks, that.serversLinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servers, serversLinks);
    }

    @Override
    public String toString() {
        return "ListServersResponse{" +
                "servers=" + servers +
                ", serversLinks=" + serversLinks +
                '}';
    }
}
