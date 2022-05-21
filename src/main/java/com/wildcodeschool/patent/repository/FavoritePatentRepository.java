package com.wildcodeschool.patent.repository;

import com.wildcodeschool.patent.entity.FavoritePatent;
import com.wildcodeschool.patent.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritePatentRepository extends JpaRepository<FavoritePatent, Long> {

    @Override
    void deleteById(@NotNull Long id);

    Optional<FavoritePatent> findFavoritePatentById(Long id);

    Optional<FavoritePatent> findFavoritePatentByPublicationCode(String publicationCode);

    Optional<FavoritePatent>  findByPublicationCodeAndUser(String publicationCode, User user);

    Boolean existsFavoritePatentByPublicationCodeAndUser(String publicationCode, User user);

    List<FavoritePatent> findFavoritePatentsByUser(User user);

}
