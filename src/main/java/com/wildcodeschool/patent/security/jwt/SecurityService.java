package com.wildcodeschool.patent.security.jwt;

public interface SecurityService {
    String validatePasswordResetToken(String token);
}
