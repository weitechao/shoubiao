package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodSugar;
import com.bracelet.service.IBloodSugarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bloodSugar")
public class BloodSugarController extends BaseController {
    @Autowired
    IBloodSugarService bloodSugarService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping("/search/latest/{token}")
    public HttpBaseDto getLatestBloodSuagr(@PathVariable String token) {
        Long user_id = checkTokenAndUser(token);
        BloodSugar bloodSugar = bloodSugarService.getLatest(user_id);
        LatestBloodSugarDto latestBloodSugarDto = null;
        if (bloodSugar != null) {
        	latestBloodSugarDto = new LatestBloodSugarDto();
        	latestBloodSugarDto.setBloodSugar(bloodSugar.getBlood_sugar());
        	latestBloodSugarDto.setTimestamp(bloodSugar.getUpload_time().getTime());
        }
        HttpBaseDto dto = new HttpBaseDto();
        dto.setData(latestBloodSugarDto);
        return dto;
    }
}
