package com.ssafy.haru.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.haru.model.HomeDealDto;
import com.ssafy.haru.model.mapper.HomeDealMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeDealService {
	private final HomeDealMapper dealMapper;
	
	public List<HomeDealDto> selectAll(String aptSeq) throws SQLException {
		return dealMapper.selectAll(aptSeq);
	}
	
	public int getCount(String aptSeq) throws SQLException {
		return dealMapper.getCount(aptSeq);
	}
}
