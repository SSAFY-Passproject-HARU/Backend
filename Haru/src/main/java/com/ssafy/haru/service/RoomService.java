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
    public RoomDto selectByRoomId(int roomId) {
        return roomMapper.selectByRoomId(roomId);
    }
    
    @Transactional
    public void likeRoom(RoomFavoriteDto roomFavoriteDto) {
        roomMapper.likeRoom(roomFavoriteDto);
    }
}
