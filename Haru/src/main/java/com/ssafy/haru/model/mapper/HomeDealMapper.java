package com.ssafy.haru.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.haru.model.HomeDealDto;

@Mapper
public interface HomeDealMapper {
	List<HomeDealDto> selectAll(String aptSeq) throws SQLException;
	int getCount(String aptSeq) throws SQLException;
}
