package cn.xxm.controller;

import cn.xxm.aop.logaop.LoggerManage;
import cn.xxm.services.CityDataService;
import cn.xxm.vo.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private CityDataService cityDataService;


    @GetMapping("/getCityList")
    @LoggerManage(description = "[获取城市列表]")
    public List<City> getCityList() {
        List<City> cityList = null;
        try {
            cityList = cityDataService.listCity();
        } catch (Exception e) {
            log.error("获取城市列表失败");
        }
        return cityList;
    }
}

