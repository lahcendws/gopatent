package com.wildcodeschool.patent.controller;

import com.wildcodeschool.patent.DTO.ToogleFavoriteDTO;
import com.wildcodeschool.patent.entity.FavoritePatent;
import com.wildcodeschool.patent.service.FavoritePatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/favoritepatent")
public class FavoritePatentController {

    @Autowired
    private FavoritePatentService favoritePatentService;


    /**
     * This method gets all favorite patents from a user;
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<FavoritePatent>> getAllFavoritePatentsByUser() {

        try {
            // Quering all favorite patents from user and adding at to this variable
            List<FavoritePatent> favoritePatentsFromUser = this.favoritePatentService.getAllFavoritesPatentsByUser();
            return new ResponseEntity<>(favoritePatentsFromUser, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("error message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This method is responsible for adding or deleting a new favorite patent into user account.
     *
     * @param publicationCode
     * @param toogleFavoriteDTO
     * @return
     */
    @PostMapping("/toogle/{publicationCode}")
    public ResponseEntity<?> toogleToFavorite(@PathVariable() String publicationCode, @RequestBody ToogleFavoriteDTO toogleFavoriteDTO) {

        boolean isFavorite = false;
        FavoritePatent patent = favoritePatentService.getFavoritePatentByPubCodeAuthenticatedUser(publicationCode);

        // Checking if there is a patent with the id taken as a parameter
        if (patent != null) {
            // delete
            try {
                favoritePatentService.deleteFavoritePatentByAuthenticatedUser(patent.getId());
            } catch (Exception e) {
                System.out.println("error delete" + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // save
            try {
                favoritePatentService.addToFavorite(publicationCode, toogleFavoriteDTO);
                isFavorite = true;
            } catch (Exception e) {
                System.out.println("error delete" + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity<>(isFavorite, HttpStatus.OK);
    }

    /**
     * This method is responsible for update the patent, specifically to add a comment, since the patent data is updated by the extern API.
     *
     * @param favoritePatentRequest
     * @return
     */
    @PutMapping("/edit")
    public ResponseEntity<?> updateFavoritePatent(@RequestBody FavoritePatent favoritePatentRequest) {

        try {
            return new ResponseEntity<>(favoritePatentService.updateFavoritePatent(favoritePatentRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deleting a favorite patent from an authenticated user.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFavoritePatent(@PathVariable() Long id) {

        try {
            favoritePatentService.deleteFavoritePatentByAuthenticatedUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("error delete" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
