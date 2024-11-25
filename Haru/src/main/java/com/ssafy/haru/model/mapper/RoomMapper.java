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
	List<RoomDto> findRooms(String sido, String gugun, String dong);
	RoomDto selectByRoomId(int roomId);
	void likeRoom(RoomFavoriteDto roomFavorite);
	void deleteFavoriteRoom(RoomFavoriteDto roomFavorite);
	List<RoomImageDto> selectRoomImagesByRoomId(int roomId);
	List<RoomDto> selectRoomsByAptSeq(String aptSeq);
	List<RoomDto> selectFavoriteRoomsByUserId(String userId);
	
    // apt_seq를 통해 apt_nm을 가져오는 쿼리 추가
    String findAptNameByAptSeq(String aptSeq);
}
