package com.wildcodeschool.patent.DTO;

import javax.validation.constraints.NotBlank;

public class ToogleFavoriteDTO {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
