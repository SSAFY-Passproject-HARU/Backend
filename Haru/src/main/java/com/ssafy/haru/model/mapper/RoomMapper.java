package com.ssafy.haru.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomFavoriteDto;
import com.ssafy.haru.model.RoomImageDto;

@Mapper
public interface RoomMapper {
	void insertRoom(RoomDto room);
	void insertRoomImages(List<RoomImageDto> fileInfos);
	RoomDto selectByRoomId(int roomId);
	void likeRoom(RoomFavoriteDto roomFavorite);
}
