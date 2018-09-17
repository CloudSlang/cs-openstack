package io.cloudslang.content.openstack.compute.entities.api;

import java.util.Objects;

public class Link {
    private String href;
    private String rel;
    private String type;

    public Link(String href, String rel, String type) {
        this.href = href;
        this.rel = rel;
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(href, link.href) &&
                Objects.equals(rel, link.rel) &&
                Objects.equals(type, link.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href, rel, type);
    }
}
