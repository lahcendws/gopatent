package com.wildcodeschool.patent.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName ;

    @NotBlank
    @NotNull
    @Size(max = 40)
    private String firstname;

    @NotBlank
    @NotNull
    @Size(max = 40)
    private String lastname;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 40)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FavoritePatent> favoritePatents;

    /**
     * Thuy: The annotation @JsonProperty is used to mark non-standard getter/setter method to be used with respect to json property.
     */
    @JsonProperty("employed")
    @Column(columnDefinition = "boolean default false")
    private boolean employed;

    @JsonProperty("createdCompany")
    @Column(columnDefinition = "boolean default false")
    private boolean createdCompany;

    @JsonProperty("companyDomain")
    @Column(columnDefinition = "boolean default false")
    private boolean companyDomain;

    public User() {
    }

    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public boolean isCreatedCompany() {
        return createdCompany;
    }

    public void setCreatedCompany(boolean createdCompany) {
        this.createdCompany = createdCompany;
    }

    public boolean isCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(boolean companyDomain) {
        this.companyDomain = companyDomain;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
