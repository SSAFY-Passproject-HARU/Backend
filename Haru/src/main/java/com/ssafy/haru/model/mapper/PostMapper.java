package com.ssafy.haru.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.haru.model.PostDto;

@Mapper
public interface PostMapper {
    // 글 목록 조회
    List<PostDto> selectAllPosts();

    // 특정 글 조회
    PostDto selectPostById(int postId);

    // 글 등록
    int insertPost(PostDto post);

    // 글 수정
    int updatePost(PostDto post);

    // 글 삭제
    int deletePost(int postId);
}
