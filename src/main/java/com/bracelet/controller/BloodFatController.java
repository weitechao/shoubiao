package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodFatDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodFat;
import com.bracelet.entity.BloodSugar;
import com.bracelet.service.IBloodFatService;
import com.bracelet.service.IBloodSugarService;
import com.bracelet.util.HttpClientGet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bloodFat")
public class BloodFatController extends BaseController {
    @Autowired
    IBloodFatService bloodFatService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping("/search/latest/{token}")
    public HttpBaseDto getLatestBloodFat(@PathVariable String token) {
    	
    	/*for(int i=0;i<3;i++){
    		String url="http://localhost:8080//bloodFat/search/latest/"+token;
    		System.out.println("URL="+url);
    		String respon=HttpClientGet.get(url);
    		System.out.println("返回数据="+respon);
    	}*/
        Long user_id = checkTokenAndUser(token);
        BloodFat bloodFat = bloodFatService.getLatest(user_id);
        LatestBloodFatDto latestBloodSugarDto = null;
      
        if (bloodFat != null) {
        	latestBloodSugarDto = new LatestBloodFatDto();
        	latestBloodSugarDto.setBloodFat(bloodFat.getBlood_fat());
        	latestBloodSugarDto.setTimestamp(bloodFat.getUpload_time().getTime());
        }
        HttpBaseDto dto = new HttpBaseDto();
        dto.setData(latestBloodSugarDto);
        return dto;
    }
}
