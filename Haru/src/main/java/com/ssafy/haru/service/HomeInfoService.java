package com.ssafy.haru.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.haru.model.HomeInfoDto;
import com.ssafy.haru.model.mapper.HomeInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeInfoService {
	private final HomeInfoMapper homeInfoMapper;
	
	public List<HomeInfoDto> selectAll() throws SQLException {
		return homeInfoMapper.selectAll();
	}
	
	public List<HomeInfoDto> select(String code) throws SQLException {
		char[] codeArr = code.toCharArray();
		
		for (int i = codeArr.length - 1; i >= 0; i--) {
			if (codeArr[i] == '0') {
				codeArr[i] = '_';
			} else {
				break;
			}
		}
		
		System.out.println(String.valueOf(codeArr));
		
		return homeInfoMapper.select(String.valueOf(codeArr));
	}

	public List<HomeInfoDto> selectBySido(String pSido) throws SQLException {
		return homeInfoMapper.selectBySido(pSido);
	}

	public List<HomeInfoDto> selectByGugun(String pSido, String pGugun) throws SQLException {;
		
		return homeInfoMapper.selectByGugun(pSido, pGugun);
	}

	public List<HomeInfoDto> selectByDong(String pSido, String pGugun, String pDong) throws SQLException {
		return homeInfoMapper.selectByDong(pSido, pGugun, pDong);
	}
	
	public List<HomeInfoDto> selectByAptSeq(String aptSeq) throws SQLException {
		return homeInfoMapper.selectByAptSeq(aptSeq);
	}

	public List<HomeInfoDto> selectAptName(String pAptName) throws SQLException {
		return homeInfoMapper.selectAptName(pAptName);
	}
}
