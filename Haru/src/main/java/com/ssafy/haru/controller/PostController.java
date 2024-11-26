package com.ssafy.haru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.haru.model.PostDto;
import com.ssafy.haru.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = { "http://localhost:5173" }, maxAge = 60000)
@Tag(name = "커뮤니티 API", description = "커뮤니티 관련 API입니다. ")
public class PostController {
	@Autowired
	private PostService postService;
	
    // 글 목록 조회
    @GetMapping
	@Operation(summary = "글 목록 조회", description = "글 목록을 조회합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "조회 성공"),
	    @ApiResponse(responseCode = "204", description = "조회 실패")
	})
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 글 조회
    @GetMapping("/{postId}")
	@Operation(summary = "특정 글 조회", description = "특정 글을 조회합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "조회 성공"),
	    @ApiResponse(responseCode = "204", description = "조회 실패")
	})
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
        PostDto post = postService.getPostById(postId);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글 등록
    @PostMapping("/create")
    @CrossOrigin(origins = { "http://localhost:5173" }, maxAge = 60000)
	@Operation(summary = "글 등록", description = "글을 등록합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "등록 성공"),
	    @ApiResponse(responseCode = "400", description = "등록 실패")
	})
    public ResponseEntity<?> createPost(@RequestBody PostDto post) {
    	System.out.println(post.toString());
    	post.setUserId("user");
        int result = postService.createPost(post);
        if (result > 0) {
            return ResponseEntity.ok(result); // 글 등록 성공 시 등록된 글 번호 반환
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 글 수정
    @PutMapping("/{postId}")
	@Operation(summary = "글 수정", description = "글을 수정합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "수정 성공"),
	    @ApiResponse(responseCode = "204", description = "수정 실패")
	})
    public ResponseEntity<Integer> updatePost(@PathVariable int postId, @RequestBody PostDto post) {
        post.setPostId(postId); // 전달된 postId로 수정 대상 설정
        int result = postService.updatePost(post);
        if (result > 0) {
            return ResponseEntity.ok(result); // 수정된 글 수 반환
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글 삭제
    @DeleteMapping("/{postId}")
	@Operation(summary = "글 삭제", description = "글을 삭제합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "삭제 성공"),
	    @ApiResponse(responseCode = "204", description = "삭제 실패")
	})
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        int result = postService.deletePost(postId);
        if (result > 0) {
            return ResponseEntity.noContent().build(); // 삭제 성공 시 204 No Content 반환
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
