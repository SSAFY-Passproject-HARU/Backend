package com.ssafy.haru.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int postId;        // 글 번호
    private String userId;      // 글쓴이 ID
    private String title;       // 제목
    private String content;     // 내용
}
