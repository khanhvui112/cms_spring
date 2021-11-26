/*-============================================================================
 * PizSoft. PROPRIETARY
 * Copyright© 2021 PizSoft.
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 *
 * This software is the confidential and proprietary information of
 * PizSoft. ("Proprietary Information"). Any use, reproduction,
 * distribution or disclosure of the software or Proprietary Information,
 * in whole or in part, must comply with the terms of the license
 * agreement, nondisclosure agreement or contract entered into with
 * PizSoft. providing access to this software.
 *
 *=============================================================================
 */
package com.fa.cms_spring.service;

import com.fa.cms_spring.config.AuthenticationConfig;
import com.fa.cms_spring.constant.AuthenticationConstant;
import com.fa.cms_spring.model.dto.AccountDto;
import com.fa.cms_spring.model.entity.Member;
import com.fa.cms_spring.repository.MemberRepository;
import com.fa.cms_spring.util.DateTimeUtil;
import com.fa.cms_spring.util.EncryptUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final MemberRepository memberRepository;

    private final AuthenticationConfig authConfig;

    public Optional<Member> getValidMember(AccountDto accountDto) {
        return memberRepository.findByEmailAndPassword(
                accountDto.getEmail(),
                EncryptUtil.hashText(accountDto.getPassword())
        );
    }

    public List<Cookie> generateCookie(AccountDto accountDto) {
        // Generate token and save to Cookie
        boolean remember = Objects.nonNull(accountDto.getRememberMe()) ?
                accountDto.getRememberMe() : false;
        int expiredSecond = remember ?
                authConfig.getExpiredRememberTokenTime() : authConfig.getExpiredTokenTime();
        LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(expiredSecond);

        Cookie emailCookie = new Cookie(AuthenticationConstant.EMAIL_COOKIE, accountDto.getEmail());
        emailCookie.setMaxAge(expiredSecond);

        Cookie accessTokenCookie = new Cookie(AuthenticationConstant.ACCESS_TOKEN_COOKIE,
                generateToken(accountDto, expiredTime));
        accessTokenCookie.setMaxAge(expiredSecond);
        accessTokenCookie.setHttpOnly(true);

        return Arrays.asList(emailCookie, accessTokenCookie);
    }

    public String generateToken(AccountDto accountDto, LocalDateTime expiredTime) {
        return generateToken(accountDto.getEmail(),
                accountDto.getPassword(),
                DateTimeUtil.convertToUnixTime(expiredTime).toString());
    }

    public String generateToken(String email, String password, String unixExpiredTime) {
        String originText = email + password + authConfig.getSecretKey() + unixExpiredTime;

        String hashText = EncryptUtil.hashText(originText);
        return hashText + "_" + unixExpiredTime;
    }

    public boolean isValidToken(Member user, String inputAccessToken) {
        String[] tokenSplit = inputAccessToken.split("_");
        if (tokenSplit.length != 2) {
            return false;
        }
        String expiredTimeStr = tokenSplit[1];
        String validToken = generateToken(user.getEmail(),
                user.getPassword(),
                expiredTimeStr);

        return validToken.equals(inputAccessToken) &&
                Long.parseLong(expiredTimeStr) >= DateTimeUtil.convertToUnixTime(LocalDateTime.now());
    }
}
