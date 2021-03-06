/*-============================================================================
 * PizSoft. PROPRIETARY
 * CopyrightÂ© 2021 PizSoft.
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

import com.fa.cms_spring.model.entity.Member;
import com.fa.cms_spring.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:phuongdp.tech@gmail.com">PhuongDP</a>
 */
@Service
@AllArgsConstructor
public class MemberService extends BaseService {

    private final MemberRepository memberRepository;

    public void addNewMember(Member member) {
        memberRepository.save(member);
    }
}
