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
package com.fa.cms_spring.controller;

import com.fa.cms_spring.constant.AuthenticationConstant;
import com.fa.cms_spring.model.dto.AccountDto;
import com.fa.cms_spring.model.entity.Member;
import com.fa.cms_spring.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@SessionAttributes(AuthenticationConstant.MEMBER_SESSION)
@Controller
@AllArgsConstructor
public class LoginController {

    private final AuthenticationService authService;

    @GetMapping({"/login", "/"})
    public String login(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "login";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute @Valid AccountDto accountDto,
                              BindingResult bindingResult,
                              Model model,
                              HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Optional<Member> memberOpt = authService.getValidMember(accountDto);
        if (memberOpt.isPresent()) {
            model.addAttribute(AuthenticationConstant.MEMBER_SESSION, memberOpt.get());
            // Generate cookie
            List<Cookie> cookies = authService.generateCookie(accountDto);
            cookies.forEach(response::addCookie);

            return "redirect:content";
        }

        model.addAttribute("invalid_account",
                "Account invalid, please try again");

        return "login";
    }
}
