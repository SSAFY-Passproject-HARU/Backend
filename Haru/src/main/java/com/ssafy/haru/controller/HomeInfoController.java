package com.ssafy.haru.controller;

import com.ssafy.haru.model.HomeInfoDto;
import com.ssafy.haru.service.HomeInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeInfoController {

	private final HomeInfoService service;

	@GetMapping("/goToList")
	public String goToList(Model model) throws SQLException {
		return "home/list";
	}

	@GetMapping("/list")
	@ResponseBody
	public List<HomeInfoDto> asyncShowList(@RequestParam(required = false) String sido, @RequestParam(required = false) String gugun,
			@RequestParam(required = false) String dong) throws SQLException {

		List<HomeInfoDto> list = new ArrayList<>();
		
		if (dong != null) {
			list = service.selectByDong(sido, gugun, dong);
		} else if (gugun != null) {
			list = service.selectByGugun(sido, gugun);
		} else if (sido != null) {
			list = service.selectBySido(sido);
		} else {
			list = service.selectAll();
		}
		
		return list;
	}

	@GetMapping("/list/{code}")
	@ResponseBody
	public List<HomeInfoDto> asyncShowList(@PathVariable("code") String code) throws SQLException {

		List<HomeInfoDto> list = service.selectByAptSeq(code);
		
		return list;
	}

	@GetMapping("/listByName")
	@ResponseBody
	public List<HomeInfoDto> asyncShowListByName(@RequestParam String aptName) throws SQLException {
		return service.selectAptName(aptName);
	}
}
