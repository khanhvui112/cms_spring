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
import com.fa.cms_spring.exception.CannotAccessException;
import com.fa.cms_spring.model.entity.Member;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
public class BaseController {

    public Member currentMemberLogin() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();

        Member member = (Member) attr.getRequest().
                            getSession().
                            getAttribute(AuthenticationConstant.MEMBER_SESSION);

        if (Objects.isNull(member)) {
            throw new CannotAccessException("User need to login before access controller");
        }

        return member;
    }
}
