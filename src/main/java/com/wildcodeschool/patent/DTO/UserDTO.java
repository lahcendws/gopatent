package com.wildcodeschool.patent.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

/**
 * Thuy: DTO created for the User entity with the informations will be sent to the front-end
 * and the informations will be received from the front-end updated by a user.
 */
public class UserDTO {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    /**
     * Thuy: The annotation @JsonProperty is used to mark non-standard getter/setter method to be used with respect to json property.
     */
    @JsonProperty("employed")
    private boolean employed;

    @JsonProperty("createdCompany")
    private boolean createdCompany;

    @JsonProperty("companyDomain")
    private boolean companyDomain;

    private String companyName;

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

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
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

    public boolean isCreatedCompany() {
        return createdCompany;
    }

    public void setCreatedCompany(boolean createdCompany) {
        this.createdCompany = createdCompany;
    }
}