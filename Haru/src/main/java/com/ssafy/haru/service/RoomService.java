package com.ssafy.haru.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomFavoriteDto;
import com.ssafy.haru.model.RoomImageDto;
import com.ssafy.haru.model.UserDto;
import com.ssafy.haru.model.mapper.RoomMapper;
import com.ssafy.haru.model.mapper.UserMapper;
import com.ssafy.haru.model.request.RecommendRoomRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomMapper roomMapper;
    private final UserMapper userMapper;
    
    @Transactional
    public int insertRoom(RoomDto room) {
        roomMapper.insertRoom(room);
        return room.getRoomId();
    }
    
    @Transactional
    public void insertRoomImages(List<RoomImageDto> fileInfos) {
        roomMapper.insertRoomImages(fileInfos);
    }
    
    public List<RoomDto> selectRoomList(String sido, String gugun, String dong) {
        return roomMapper.findRooms(sido, gugun, dong);
    }
    
    @Transactional
    public RoomDto selectByRoomId(int roomId) {
        return roomMapper.selectByRoomId(roomId);
    }
    
    @Transactional
    public void likeRoom(RoomFavoriteDto roomFavoriteDto) {
        roomMapper.likeRoom(roomFavoriteDto);
    }
    
    @Transactional
    public List<RoomImageDto> getRoomImages(int roomId) {
        return roomMapper.selectRoomImagesByRoomId(roomId);
    }
    
    @Transactional
    public List<RoomDto> getRoomsByAptSeq(String aptSeq) {
        return roomMapper.selectRoomsByAptSeq(aptSeq);
    }
    
    @Transactional
    public List<RoomDto> getFavoriteRoomsByUserId(String userId) {
        return roomMapper.selectFavoriteRoomsByUserId(userId);
    }
    
    @Transactional
    public void removeFavorite(RoomFavoriteDto roomFavoriteDto) {
        roomMapper.deleteFavoriteRoom(roomFavoriteDto);
    }
      
    // apt_seq로 apt_nm을 가져오는 메서드 추가
    public String getAptNameByAptSeq(String aptSeq) {
        return roomMapper.findAptNameByAptSeq(aptSeq);

    }
    
    public List<RoomDto> getRecommendations(String userId) {    	
    	// Step 1: 유저가 찜한 매물의 평균 속성 계산
    	RecommendRoomRequestDto preference = roomMapper.findUserPreferences(userId);
    	
    	UserDto user = userMapper.select(userId);
    	List<RoomDto> recommendedRooms;
    	
    	if (preference == null) {
    		System.out.println("preference null");
    		recommendedRooms = roomMapper.findRooms(user.getSido(), user.getGugun(), user.getDong());
    	} else {
    		System.out.println("preference exist");
        	preference.setSido(user.getSido());
        	preference.setGugun(user.getGugun());
        	preference.setDong(user.getDong());
        	
            // Step 2: 거리 기반으로 가장 가까운 매물 추천
            recommendedRooms = roomMapper.findRecommendedRoomsByDistance(preference);
    	}

        return recommendedRooms;
    }
}
