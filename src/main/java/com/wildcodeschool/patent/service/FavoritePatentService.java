package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.DTO.ToogleFavoriteDTO;
import com.wildcodeschool.patent.entity.FavoritePatent;
import com.wildcodeschool.patent.entity.User;
import com.wildcodeschool.patent.repository.FavoritePatentRepository;
import com.wildcodeschool.patent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritePatentService {

    private final FavoritePatentRepository favoritePatentRepository;

    private final UserRepository userRepository;

    @Autowired
    public FavoritePatentService(FavoritePatentRepository favoritePatentRepository, UserRepository userRepository) {
        this.favoritePatentRepository = favoritePatentRepository;
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getByEmail(authentication.getName());
    }


    public FavoritePatent getFavoritePatentByAuthenticatedUser(FavoritePatent favoritePatent) {
        User user = this.getAuthenticatedUser();
        return favoritePatentRepository.findByPublicationCodeAndUser(favoritePatent.getPublicationCode(), user)
                .orElseThrow(() -> new RuntimeException("FavoritePatent by User ID " + user.getId() + " and Publication Code " + favoritePatent.getPublicationCode() + " was not found"));
    }

    public FavoritePatent getFavoritePatentByPubCodeAuthenticatedUser(String publicationCode) {
        User user = this.getAuthenticatedUser();
        return favoritePatentRepository.findByPublicationCodeAndUser(publicationCode, user)
                .orElse(null);
    }


    public Optional<FavoritePatent> getFavoritePatentById(Long id) {
        return favoritePatentRepository.findFavoritePatentById(id);
    }


    public void addToFavorite(String publicationCode, ToogleFavoriteDTO toogleFavoriteDTO) {

        User user = this.getAuthenticatedUser();

        FavoritePatent userFavPatent = new FavoritePatent(user, publicationCode, toogleFavoriteDTO.getTitle());
        favoritePatentRepository.save(userFavPatent);

    }

    public List<FavoritePatent> getAllFavoritesPatentsByUser() {
        User user = this.getAuthenticatedUser();
        return favoritePatentRepository.findFavoritePatentsByUser(user);
    }


    public void deleteFavoritePatentByAuthenticatedUser(Long id) {
        User user = this.getAuthenticatedUser();
        Optional<FavoritePatent> patentId = this.getFavoritePatentById(id);
        if (patentId.isPresent() && patentId.get().getUser().equals(user)) {
            favoritePatentRepository.deleteById(id);
        }
    }


    public FavoritePatent updateFavoritePatent(FavoritePatent favoritePatent) {

        Optional<FavoritePatent> favoritePatentFromUser = Optional.ofNullable(this.getFavoritePatentByAuthenticatedUser(favoritePatent));
        FavoritePatent currentFavPatent = favoritePatentFromUser.get();

        currentFavPatent.setPatentTitle(favoritePatent.getPatentTitle());
        currentFavPatent.setComment(favoritePatent.getComment());

        return favoritePatentRepository.save(currentFavPatent);
    }
}
