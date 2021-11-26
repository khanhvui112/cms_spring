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

import com.fa.cms_spring.model.entity.Member;
import com.fa.cms_spring.service.MemberService;
import com.fa.cms_spring.util.EncryptUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Controller
@AllArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        Member member = new Member();
        member.setEmail("test@g.com");
        member.setUserName("TEST");
        member.setPassword(EncryptUtil.hashText("123456"));

        memberService.addNewMember(member);

        return "login";
    }
}
