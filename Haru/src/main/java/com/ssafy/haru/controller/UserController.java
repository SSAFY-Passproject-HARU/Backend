package com.ssafy.haru.controller;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.haru.model.UserDto;
import com.ssafy.haru.model.request.UserLoginRequestDto;
import com.ssafy.haru.model.request.UserPasswordResetRequestDto;
import com.ssafy.haru.model.request.UserUpdateRequestDto;
import com.ssafy.haru.model.response.UserInfoResponseDto;
import com.ssafy.haru.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "User 관련 Session 기반 API입니다. ")
@Slf4j
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "로그인")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Session 사용 로그인 완료"),
        @ApiResponse(responseCode = "404", description = "로그인 실패"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody UserLoginRequestDto dto, HttpSession session) throws SQLException {
    	System.out.println("dto.getId(): " + dto.getId());
    	System.out.println("dto.getPassword(): " + dto.getPassword());
        UserDto loginedUser = userService.login(dto.getId(), dto.getPassword());

        if (loginedUser != null) {
            session.setAttribute("user", loginedUser);
        } else {
            throw new RuntimeException("로그인 실패");
        }
    }

    // 로그아웃
    @DeleteMapping("/logout")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 완료")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    // 회원가입
    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "회원가입")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
        @ApiResponse(responseCode = "400", description = "회원 생성 실패"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserDto dto) throws SQLException {
        int result = userService.insert(dto);

        if (result != 1) {
            throw new RuntimeException("회원 생성 실패");
        }
    }

    // 회원 정보 조회
    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "회원 정보 불러오기")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "401", description = "로그인 안된 상태로 인해 실패"),
    })
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponseDto info(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("로그인 상태 아님");
        }

        UserInfoResponseDto dto = new UserInfoResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

        return dto;
    }

    // 회원 삭제
    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "회원 삭제")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "401", description = "로그인 안된 상태로 인해 실패"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void delete(HttpSession session) throws SQLException {
        UserDto user = (UserDto) session.getAttribute("user");

        if (user != null) {
            userService.delete(user.getId());
            session.invalidate();
        } else {
            throw new RuntimeException("로그인 상태 아님");
        }
    }

    // 회원 수정
    @PutMapping
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "회원 정보 갱신")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "로그인 안된 상태 또는 Blank 상태의 데이터 때문에 실패"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserUpdateRequestDto dto, HttpSession session) throws SQLException {
        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null || dto.getPassword().isBlank() || dto.getName().isBlank() || dto.getNickname().isBlank() || dto.getEmail().isBlank() || dto.getSido().isBlank() || dto.getGugun().isBlank() || dto.getDong().isBlank()) {
            throw new RuntimeException("잘못된 요청");
        }

        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setSido(dto.getSido());
        user.setGugun(dto.getGugun());
        user.setDong(dto.getDong());

        int result = userService.update(user);

        if (result != 1) {
            throw new RuntimeException("회원 정보 갱신 실패");
        }

        session.setAttribute("user", user); // 세션 업데이트
    }

    // 비밀번호 재설정
    @PutMapping("/passwordReset")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
    @Operation(summary = "회원 비밀번호 재설정")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "맞지 않는 정보로 인해 실패"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void passwordReset(@RequestBody UserPasswordResetRequestDto dto) throws SQLException {
        UserDto user = userService.selectUser(dto.getId());

        if (user != null && user.getEmail().equals(dto.getEmail()) && user.getName().equals(dto.getName())) {
            user.setPassword(dto.getPassword());
            userService.update(user);
        } else {
            throw new RuntimeException("비밀번호 재설정 실패");
        }
    }
}