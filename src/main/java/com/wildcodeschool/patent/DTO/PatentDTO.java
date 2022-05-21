package com.wildcodeschool.patent.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

/**
 * patent entity, not sure that it have to be in database
 */
public class PatentDTO {

    private String patentNumber;
    private String title;

    private Boolean isFavorite;
    private LocalDate releaseDate;
    private String applicantName;
    private HashSet<String> inventorsArrayList;
    private String familyId;
    private String ipc;
    private HashSet<String> domains;
    private HashSet<String> bigDomains;
    private String espaceNetUrl;
    private String patentAbstract;
    private String figure;

    public PatentDTO(String patentNumber, String title, Boolean isFavorite, String releaseDate, String applicantName, HashSet<String> inventorsArrayList, String familyId, String ipc, HashSet<String> domains, HashSet<String> bigDomains, String espaceNetUrl, String patentAbstract, String figure) {
        this.patentNumber = patentNumber;
        this.title = title;
        this.isFavorite = isFavorite;
        this.releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.BASIC_ISO_DATE);
        this.applicantName = applicantName;
        this.inventorsArrayList = inventorsArrayList;
        this.familyId = familyId;
        this.ipc = ipc;
        this.domains = domains;
        this.bigDomains = bigDomains;
        this.espaceNetUrl = espaceNetUrl;
        this.patentAbstract = patentAbstract;
        this.figure = figure;
    }

    @Override
    public String toString() {
        return "PatentDTO{" +
                "patentNumber='" + patentNumber + '\'' +
                ", title='" + title + '\'' +
                ", isFavorite=" + isFavorite +
                ", releaseDate=" + releaseDate +
                ", applicantName='" + applicantName + '\'' +
                ", inventorsArrayList=" + inventorsArrayList +
                ", familyId='" + familyId + '\'' +
                ", ipc='" + ipc + '\'' +
                ", domains=" + domains +
                ", bigDomains=" + bigDomains +
                ", espaceNetUrl='" + espaceNetUrl + '\'' +
                ", patentAbstract='" + patentAbstract + '\'' +
                ", figure='" + figure + '\'' +
                '}';
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    public void setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public HashSet<String> getInventorsArrayList() {
        return inventorsArrayList;
    }

    public void setInventorsArrayList(HashSet<String> inventorsArrayList) {
        this.inventorsArrayList = inventorsArrayList;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getEspaceNetUrl() {
        return espaceNetUrl;
    }

    public void setEspaceNetUrl(String espaceNetUrl) {
        this.espaceNetUrl = espaceNetUrl;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public void setPatentAbstract(String patentAbstract) {
        this.patentAbstract = patentAbstract;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getIpc() {
        return ipc;
    }

    public void setIpc(String ipc) {
        this.ipc = ipc;
    }

    public HashSet<String> getDomains() {
        return domains;
    }

    public void setDomains(HashSet<String> domains) {
        this.domains = domains;
    }

    public HashSet<String> getBigDomains() {
        return bigDomains;
    }

    public void setBigDomains(HashSet<String> bigDomains) {
        this.bigDomains = bigDomains;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
