package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.compute.entities.Link;

import java.util.List;
import java.util.Objects;

public class Servers {
    private List<Link> links;

    private String id;
    private String name;

    public Servers(List<Link> links, String id, String name) {
        this.links = links;
        this.id = id;
        this.name = name;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servers servers = (Servers) o;
        return Objects.equals(links, servers.links) &&
                Objects.equals(id, servers.id) &&
                Objects.equals(name, servers.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(links, id, name);
    }

    @Override
    public String toString() {
        return "Servers{" +
                "links=" + links +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
