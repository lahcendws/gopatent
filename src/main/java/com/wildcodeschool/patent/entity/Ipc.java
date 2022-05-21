package com.wildcodeschool.patent.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ipc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipc;

    @ManyToMany
    @JoinTable(name = "domain_ipc",
            joinColumns = @JoinColumn(name = "ipc_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id"))
    private List<Domain> domains;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpc() {
        return ipc;
    }

    public void setIpc(String ipc) {
        this.ipc = ipc;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }
}
