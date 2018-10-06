package cn.xxm.controller;

import cn.xxm.aop.logaop.LoggerManage;
import cn.xxm.service.WeatherDataService;
import cn.xxm.vo.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxm
 * @create 2018-08-30 20:58
 */

@RestController
@Slf4j
public class WeatherController {


    @Autowired
    private WeatherDataService weatherDataService;


    @GetMapping("/getWeatherByCityId/{cityId}/{cityName}")
//    @LoggerManage(description = "[根据城市id查询天气预报]")
    public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId,@PathVariable("cityName") String cityName){
        WeatherResponse response = weatherDataService.getDataByCityId(cityId,cityName);
        log.info("查询到的response的信息是:"+response);
        return response;
    }



    @GetMapping("/syncWeather/{cityId}/{cityName}")
//    @LoggerManage(description = "[定时任务更新天气预报]")
    public WeatherResponse syncWeather(@PathVariable("cityId") String cityId,@PathVariable("cityName") String cityName){
        WeatherResponse response = weatherDataService.syncWeatherByCityId(cityId,cityName);
        log.info("定时任务正在同步的天气的数据是response:"+response);
        return response;
    }

}

