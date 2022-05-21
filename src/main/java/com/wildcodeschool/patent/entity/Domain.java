package com.wildcodeschool.patent.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bigDomainName;
    private String domainName;

    @ManyToMany
    @JoinTable(name = "domain_ipc",
        joinColumns = @JoinColumn(name = "domain_id"),
        inverseJoinColumns = @JoinColumn(name = "ipc_id"))
    private List<Ipc> ipcs;

    public Domain() {

    }

    public Domain(List<Ipc> ipcs) {

        this.ipcs = ipcs;
    }

    public Domain(String bigDomainName, String domainName, List<Ipc> ipcs) {
        this.bigDomainName = bigDomainName;
        this.domainName = domainName;
        this.ipcs = ipcs;
    }

    public String getBigDomainName() {
        return bigDomainName;
    }

    public void setBigDomainName(String bigDomainName) {
        this.bigDomainName = bigDomainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "bigDomainName='" + bigDomainName + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }
}
