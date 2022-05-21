package com.wildcodeschool.patent.security.jwt;

import com.wildcodeschool.patent.entity.PasswordResetToken;
import com.wildcodeschool.patent.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    /**
     * Checks if the given token is valid for the user.
     * @param token
     * @return
     */
    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if (!isTokenFound(passToken)) {
            return "le lien est invalide";
        } else if (isTokenExpired(passToken)) {
            return "le lien à expiré";
        }
        return null;
    }

    /**
     * Checks if the token is found in the database.
     * @param passToken
     * @return
     */
    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    /**
     * Checks if the token is expired.
     * @param passToken
     * @return
     */
    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
