package cn.xxm.controller;

import cn.xxm.aop.logaop.LoggerManage;
import cn.xxm.services.WeatherDataService;
import cn.xxm.vo.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xxm
 * @create 2018-08-30 20:58
 */

@RestController
@Slf4j
public class WeatherController {


    @Autowired
    private WeatherDataService weatherDataService;


    @GetMapping("/getWeather/{cityName}")
    @LoggerManage(description = "[根据城市查询天气预报]")
    public WeatherResponse getWeather(@PathVariable("cityName") String cityName){
        WeatherResponse response = weatherDataService.getDataByCityName(cityName);
        return response;
    }


}

