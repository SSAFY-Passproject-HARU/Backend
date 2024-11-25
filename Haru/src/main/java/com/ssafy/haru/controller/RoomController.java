package com.ssafy.haru.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomFavoriteDto;
import com.ssafy.haru.model.RoomImageDto;
import com.ssafy.haru.model.response.RecommendRoomResponseDto;
import com.ssafy.haru.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = { "http://localhost:5173/" }, maxAge = 60000)
@Tag(name = "매물 API", description = "매물 관련 API입니다. ")
public class RoomController {
	
	@Autowired
    private RoomService roomService;
	
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
	        String realPath = "C:/Users/SSAFY/Desktop/project/Frontend/public/images";
	        // String realPath = "C:/SSAFY/workspace/pass-project/passproject_final/Frontend/public/images";
	        String saveFolder = realPath + File.separator + today;
	        File folder = new File(saveFolder);
	        if (!folder.exists()) {
	        	folder.mkdirs();
	        	if (!folder.exists()) {
		        	System.out.println("폴더 안 만들어짐");
		        }
	        }
	        
	        List<RoomImageDto> fileInfos = new ArrayList<>();
	        for (MultipartFile mfile : files) {
	            String originalFileName = mfile.getOriginalFilename();
	            if (!originalFileName.isEmpty()) {
	                String saveFileName = UUID.randomUUID().toString()
	                        + originalFileName.substring(originalFileName.lastIndexOf('.'));
	                
	                // 파일 저장
	                try {
	                    mfile.transferTo(new File(folder, saveFileName));
	                    System.out.println("파일 저장 성공: " + folder + ", " + saveFileName);
	                } catch (IOException e) {
	                    e.printStackTrace();  // 오류 상세 출력
	                    System.out.println("파일 저장 실패: " + saveFileName);
	                }
	                
	                // roomimages 테이블에 저장할 데이터 생성
	                RoomImageDto fileInfoDto = new RoomImageDto();
	                fileInfoDto.setRoomId(roomId);  // 외래 키 설정
	                fileInfoDto.setImageUrl(today + '/' + saveFileName);
	                
	                fileInfos.add(fileInfoDto);
	            }
	        }
	        // 3. roomimages 테이블에 이미지 정보 저장
	        roomService.insertRoomImages(fileInfos);
	        response.put("success", true);
	    }
	    
	    return ResponseEntity.ok(response);
	}
	
	// 매물 목록 조회
	@GetMapping("/detail")
	@Operation(summary = "매물 목록 조회", description = "여러 매물 정보를 한꺼번에 조회합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "매물 목록 조회 성공"),
	    @ApiResponse(responseCode = "204", description = "조회된 매물이 없음")
	})
	public ResponseEntity<?> getRoomDetailList(
	        @RequestParam(required = true) String sido,
	        @RequestParam(required = true) String gugun,
	        @RequestParam(required = true) String dong
	) {
	    try {
	        // 조건에 따라 매물 목록 조회
	        List<RoomDto> roomList = roomService.selectRoomList(sido, gugun, dong);
	        if (roomList != null && !roomList.isEmpty()) {
	            return ResponseEntity.ok(roomList); // 조회된 매물 리스트 반환
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("조회된 매물이 없습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("매물 조회 중 오류가 발생했습니다.");
	    }
	}

	// 매물 정보 조회
	@GetMapping("/detail/{roomId}")
	@Operation(summary = "매물 상세 조회", description = "특정 매물의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "매물 정보 조회 성공"),
	    @ApiResponse(responseCode = "404", description = "매물 정보가 존재하지 않음")
	})
	public ResponseEntity<?> getRoomDetail(@PathVariable int roomId) {
	    RoomDto roomDetail = roomService.selectByRoomId(roomId);
	    if (roomDetail != null) {
	        return ResponseEntity.ok(roomDetail);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("매물 정보를 찾을 수 없습니다.");
	    }
	}
	
	@GetMapping("/detail/{roomId}/apt-name")
	public ResponseEntity<?> getAptName(@PathVariable int roomId) {
	    RoomDto room = roomService.selectByRoomId(roomId);
	    if (room != null) {
	        String aptSeq = room.getAptSeq();
	        String aptName = roomService.getAptNameByAptSeq(aptSeq);
	        if (aptName != null) {
	            return ResponseEntity.ok(aptName); // 아파트 이름만 반환
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아파트 정보를 찾을 수 없습니다.");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("매물 정보를 찾을 수 없습니다.");
	    }
	}
	
	// 매물 좋아요 추가
	@PostMapping("/like")
	@Operation(summary = "매물 좋아요", description = "특정 매물에 좋아요를 추가합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "좋아요 성공"),
	    @ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	public ResponseEntity<?> likeRoom(@RequestBody RoomFavoriteDto roomFavoriteDto) {
	    try {
	        roomService.likeRoom(roomFavoriteDto);
	        return ResponseEntity.ok().body("좋아요가 성공적으로 추가되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("좋아요 처리 중 오류가 발생했습니다.");
	    }
	}
	
	// 매물 좋아요 삭제
	@DeleteMapping("/removelike")
	@Operation(summary = "매물 좋아요 삭제", description = "특정 매물에 누른 좋아요를 삭제합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
	    @ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
    public String removeFavorite(@RequestBody RoomFavoriteDto roomFavoriteDto) {
        try {
            roomService.removeFavorite(roomFavoriteDto);
            return "Favorite removed successfully";
        } catch (Exception e) {
            return "Error removing favorite: " + e.getMessage();
        }
    }
	
	// 매물 좋아요 목록
	@GetMapping("/favorites")
	@Operation(summary = "매물 좋아요 목록", description = "아이디로 찜한 매물 목록을 조회합니다")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "조회 성공"),
	    @ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
    public List<RoomDto> getFavoriteRoomsByUserId(@RequestParam String userId) {
        return roomService.getFavoriteRoomsByUserId(userId);
    }
	
	@GetMapping("/images/{roomId}")
	@Operation(summary = "매물 이미지 조회", description = "특정 매물의 이미지 목록을 조회합니다.")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "매물 이미지 조회 성공"),
		    @ApiResponse(responseCode = "400", description = "잘못된 요청")
		})
    public ResponseEntity<?> getRoomImages(@PathVariable int roomId) {
        List<RoomImageDto> imageList = roomService.getRoomImages(roomId);
        if (imageList != null && !imageList.isEmpty()) {
            return ResponseEntity.ok(imageList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이미지 정보를 찾을 수 없습니다.");
        }
    }
	
	// aptSeq로 매물 리스트 조회
    @GetMapping("/list/{aptSeq}")
    @Operation(summary = "매물 리스트", description = "아파트 ID로 매물 리스트를 조회합니다.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "리스트 조회 성공"),
	    @ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
    public ResponseEntity<?> getRoomsByAptSeq(@PathVariable String aptSeq) {
        List<RoomDto> roomList = roomService.getRoomsByAptSeq(aptSeq);
        if (roomList != null && !roomList.isEmpty()) {
            return ResponseEntity.ok(roomList);
        } else {
            return ResponseEntity.status(404).body("해당 아파트에 대한 매물 정보가 없습니다.");
        }
    }
    
    // 추천 매물 리스트 조회
    @GetMapping("/recommendation/{userId}")
    public ResponseEntity<List<RecommendRoomResponseDto>> recommendRooms(@PathVariable String userId) {
        List<RecommendRoomResponseDto> recommendedRooms = roomService.getRecommendations(userId);
        return ResponseEntity.ok(recommendedRooms);
    }
}
