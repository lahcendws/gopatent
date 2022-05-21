package com.wildcodeschool.patent;

import com.wildcodeschool.patent.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Configuration
@EnableScheduling
public class CronSchedule {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    /**
     * Delete expired password reset tokens
     * s mm hh dd MM weekdays (* = all)
     */
    @Scheduled(cron = "0 00 00 * * MON-THU")
    public void deleteResetTokenPassword() {

        Date date = new Date();

        // Delete all password reset tokens that are older than 1 day
        passwordResetTokenRepository.deleteByExpiryDateBefore(date);
    }
}
