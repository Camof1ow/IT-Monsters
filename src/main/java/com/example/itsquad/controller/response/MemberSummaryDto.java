package com.example.itsquad.controller.response;

import com.example.itsquad.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSummaryDto {

    private Long id;
    private String email;
    private String nickname;
    private String profileImg;


    public MemberSummaryDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImg = member.getProfileImg();
    }
}
