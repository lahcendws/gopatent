package com.wildcodeschool.patent.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wildcodeschool.patent.entity.FavoritePatent;
import com.wildcodeschool.patent.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FavoritePatentRepositoryTest {

    @Autowired
    private FavoritePatentRepository favoritePatentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Test if the favorite patent is saved in the database
     */
    @Test
    public void testSaveFavoritePatent() {
        /**
         * Create a user
         */
        User user = new User();
        user.setEmail("thuytest@gmail.com");
        user.setPassword("123456");
        user.setFirstname("Thuy");
        user.setLastname("Test");
        userRepository.save(user);

        /**
         * Create a favorite patent for this user
         */
        FavoritePatent favoritePatent = new FavoritePatent();
        favoritePatent.setPublicationCode("CA123456");
        favoritePatent.setUser(user);
        FavoritePatent savedFavoritePatent = favoritePatentRepository.save(favoritePatent);

        /**
         * Check if the favorite patent is saved in the database
         */
        assertThat(savedFavoritePatent.getId()).isNotNull();

        /**
         * Check if the favorite patent is saved with the right user
         */
        assertThat(savedFavoritePatent.getUser().getEmail()).isEqualTo("thuytest@gmail.com");
    }


    /**
     * Test if the favorite patents are found in the database
     */
    @Test
    public void getAllFavoritePatents() {

        /**
         * Create a user
         */
        User user = new User();
        user.setEmail("gerseytest@gmail.com");
        user.setPassword("123456");
        user.setFirstname("Gersey");
        user.setLastname("Stelmach");
        userRepository.save(user);

        FavoritePatent favPatent1 = new FavoritePatent(user, "Test", "Test");
        FavoritePatent favPatent2 = new FavoritePatent(user, "Test", "Test");

        List<FavoritePatent> favoritePatents = new ArrayList<FavoritePatent>();
        favoritePatents.add(favPatent1);
        favoritePatents.add(favPatent2);

        favoritePatentRepository.saveAll(favoritePatents);

        List<FavoritePatent> favoritePatentsFromDb = favoritePatentRepository.findAll();

        assertThat(favoritePatentsFromDb.size()).isGreaterThan(0);

    }

    /**
     * Test update favorite patent
     */

    @Test
    public void testUpdateFavoritePatents(){

        /**
         * Create a user
         */
        User user = new User();
        user.setEmail("testDelete@gmail.com");
        user.setPassword("azerty");
        user.setFirstname("testName");
        user.setLastname("testLastName");
        userRepository.save(user);

        List<FavoritePatent> favoritePatents = new ArrayList<FavoritePatent>();
        FavoritePatent favoritePatentTest = new FavoritePatent(user, "Te123456", "TestTitle");
        favoritePatents.add(favoritePatentTest);
        FavoritePatent favoritePatent1Updated = favoritePatentRepository.save(favoritePatentTest);

        Optional<FavoritePatent> favoritePatent1 = favoritePatentRepository.findFavoritePatentByPublicationCode("Te123456");
        favoritePatent1.get().setPublicationCode("Test00000");

        assertThat(favoritePatent1Updated.getPublicationCode()).isEqualTo("Test00000");
    }

    /**
     * Test if the favorite patent is deleted in the database
     */
    @Test
    public void testDeleteFavoritePatent() {
        /**
         * Create a user
         */
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setFirstname("Thuy");
        user.setLastname("Test");
        userRepository.save(user);

        /**
         * Create a favorite patent for this user
         */
        FavoritePatent favoritePatent = new FavoritePatent();
        favoritePatent.setPublicationCode("CA123456");
        favoritePatent.setUser(user);
        FavoritePatent savedFavoritePatent = favoritePatentRepository.save(favoritePatent);

        /**
         * Check if the favorite patent is saved in the database
         */
        assertThat(savedFavoritePatent.getId()).isNotNull();

        /**
         * Delete this saved favorite patent
         */
        favoritePatentRepository.deleteById(savedFavoritePatent.getId());

        /**
         * Check if the favorite patent is deleted in the database
         */
        Optional<FavoritePatent> deletedFavoritePatent = favoritePatentRepository.findFavoritePatentByPublicationCode("CA123456");
        assertThat(deletedFavoritePatent).isEmpty();
    }

}
