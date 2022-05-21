package com.wildcodeschool.patent.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
public class FavoritePatent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private String publicationCode;

    @Column(columnDefinition ="LONGTEXT")
    private String comment;

    @Column(columnDefinition ="TEXT")
    private String patentTitle;

    public FavoritePatent() {}


    public FavoritePatent(User userId, String publicationCode, String patentTitle) {
        this.user = userId;
        this.publicationCode = publicationCode;
        this.patentTitle = patentTitle;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public String getPublicationCode() {
        return publicationCode;
    }

    public void setPublicationCode(String publicationCode) {
        this.publicationCode = publicationCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public String getPatentTitle() {
        return patentTitle;
    }

    public void setPatentTitle(String patentTitle) {
        this.patentTitle = patentTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FavoritePatent{" +
                "id=" + id +
                ", userId=" + user +
                ", publicationCode='" + publicationCode + '\'' +
                ", comment='" + comment + '\'' +
                ", patentTitle='" + patentTitle + '\'' +
                '}';
    }
}
