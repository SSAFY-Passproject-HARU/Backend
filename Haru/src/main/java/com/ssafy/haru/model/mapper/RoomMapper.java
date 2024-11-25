package com.ssafy.haru.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.haru.model.RoomDto;
import com.ssafy.haru.model.RoomFavoriteDto;
import com.ssafy.haru.model.RoomImageDto;
import com.ssafy.haru.model.request.RecommendRoomRequestDto;
import com.ssafy.haru.model.response.RecommendRoomResponseDto;

@Mapper
public interface RoomMapper {
	void insertRoom(RoomDto room);
	void insertRoomImages(List<RoomImageDto> fileInfos);
	List<RoomDto> findRooms(String sido, String gugun, String dong);
	RoomDto selectByRoomId(int roomId);
	void likeRoom(RoomFavoriteDto roomFavorite);
	List<RoomImageDto> selectRoomImagesByRoomId(int roomId);
	List<RoomDto> selectRoomsByAptSeq(String aptSeq);
	
    // apt_seq를 통해 apt_nm을 가져오는 쿼리 추가
    String findAptNameByAptSeq(String aptSeq);
    
    // 유저가 찜한 매물의 평균 속성 계산
    RecommendRoomRequestDto findUserPreferences(String userId);
    
    // 거리 기반으로 가장 가까운 매물 추천
    List<RecommendRoomResponseDto> findRecommendedRoomsByDistance(RecommendRoomRequestDto preference);
}
