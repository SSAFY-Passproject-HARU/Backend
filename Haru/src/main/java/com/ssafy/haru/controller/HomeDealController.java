package com.ssafy.haru.controller;

import com.ssafy.haru.model.HomeDealDto;
import com.ssafy.haru.service.HomeDealService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/homeDetail")
@RequiredArgsConstructor
public class HomeDealController {

    private final HomeDealService service;

    @GetMapping("/list")
    public ResponseEntity<List<HomeDealDto>> asyncShowDetailList(@RequestParam String aptSeq, Model model) throws SQLException, IOException {
        List<HomeDealDto> list = service.selectAll(aptSeq);
        return ResponseEntity.ok(list);
    }
}
