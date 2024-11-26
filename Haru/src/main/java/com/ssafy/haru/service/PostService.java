package com.ssafy.haru.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.haru.model.PostDto;
import com.ssafy.haru.model.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;
	
	@Transactional
    public List<PostDto> getAllPosts() {
        return postMapper.selectAllPosts();
    }

	@Transactional
    public PostDto getPostById(int postId) {
        return postMapper.selectPostById(postId);
    }

	@Transactional
    public int createPost(PostDto post) {
		return postMapper.insertPost(post);
    }

	@Transactional
    public int updatePost(PostDto post) {
        return postMapper.updatePost(post);
    }

	@Transactional
    public int deletePost(int postId) {
        return postMapper.deletePost(postId);
    }
}
