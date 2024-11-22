package com.ssafy.haru.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.haru.model.HomeInfoDto;

@Mapper
public interface HomeInfoMapper {
	List<HomeInfoDto> selectAll() throws SQLException;
	
	List<HomeInfoDto> select(String dongCode);

	List<HomeInfoDto> selectBySido(String pSido) throws SQLException;

	List<HomeInfoDto> selectByGugun(String pSido, String pGugun) throws SQLException;

	List<HomeInfoDto> selectByDong(String pSido, String pGugun, String pDong) throws SQLException;

	List<HomeInfoDto> selectByAptSeq(String aptSeq) throws SQLException;
	
	List<HomeInfoDto> selectAptName(String pAptName) throws SQLException;
}