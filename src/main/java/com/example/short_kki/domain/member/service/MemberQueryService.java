package com.example.short_kki.domain.member.service;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 멤버 입니다."));
    }
}
