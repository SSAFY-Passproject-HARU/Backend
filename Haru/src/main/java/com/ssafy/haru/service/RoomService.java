package com.ssafy.haru.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomFavoriteDto;
import com.ssafy.haru.model.RoomImageDto;
import com.ssafy.haru.model.mapper.RoomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomMapper roomMapper;
    
    @Transactional
    public int insertRoom(RoomDto room) {
        roomMapper.insertRoom(room);
        return room.getRoomId();
    }
    
    @Transactional
    public void insertRoomImages(List<RoomImageDto> fileInfos) {
        roomMapper.insertRoomImages(fileInfos);
    }
    
    @Transactional
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
      
    // apt_seq로 apt_nm을 가져오는 메서드 추가
    public String getAptNameByAptSeq(String aptSeq) {
        return roomMapper.findAptNameByAptSeq(aptSeq);

    }
}
