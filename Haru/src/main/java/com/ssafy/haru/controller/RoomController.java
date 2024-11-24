package com.ssafy.haru.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomImageDto;
import com.ssafy.haru.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = { "http://localhost:5173/" }, maxAge = 60000)
@Tag(name = "매물 API", description = "매물 관련 API입니다. ")
public class RoomController {
	
	@Autowired
    private RoomService roomService;
	@Autowired
	private ServletContext servletContext;
	
	// 매물 등록
	@PostMapping("/register")
	@CrossOrigin(origins = { "http://localhost:5173/" }, maxAge = 60000)
	@Operation(summary = "매물 등록", description = "매물 등록")
	public ResponseEntity<?> registerRoom(@RequestPart RoomDto roomDto, @RequestParam MultipartFile[] files) throws Exception {
	    Map<String, Object> response = new HashMap<>();
	    
	    // 1. roominfos 테이블에 매물 정보 저장
	    // 저장된 room_id 가져오기
	    int roomId = roomService.insertRoom(roomDto); // roomService.insertRoom에서 roomId 반환하도록 구현 필요
	    System.out.println("roomId: " + roomId);
	    // 2. 파일 처리
	    if (files != null && files.length > 0 && !files[0].isEmpty()) {
	        String today = new SimpleDateFormat("yyMMdd").format(new Date());
	        String realPath = servletContext.getRealPath("/assets/upload/");
	        String saveFolder = realPath + File.separator + today;
	        File folder = new File(saveFolder);
	        if (!folder.exists()) folder.mkdirs();
	        
	        List<RoomImageDto> fileInfos = new ArrayList<>();
	        for (MultipartFile mfile : files) {
	            String originalFileName = mfile.getOriginalFilename();
	            if (!originalFileName.isEmpty()) {
	                String saveFileName = UUID.randomUUID().toString()
	                        + originalFileName.substring(originalFileName.lastIndexOf('.'));
	                
	                // 파일 저장
	                mfile.transferTo(new File(folder, saveFileName));
	                
	                // roomimages 테이블에 저장할 데이터 생성
	                RoomImageDto fileInfoDto = new RoomImageDto();
	                fileInfoDto.setRoomId(roomId);  // 외래 키 설정
	                fileInfoDto.setImageUrl("/assets/upload/" + today + "/" + saveFileName);
	                
	                fileInfos.add(fileInfoDto);
	            }
	        }
	        // 3. roomimages 테이블에 이미지 정보 저장
	        roomService.insertRoomImages(fileInfos);
	        response.put("success", true);
	    }
	    
	    return ResponseEntity.ok(response);
	}

}
