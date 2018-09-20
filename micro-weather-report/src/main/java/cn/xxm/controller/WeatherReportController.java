package cn.xxm.controller;

import cn.xxm.aop.logaop.LoggerManage;
import cn.xxm.service.CityClientService;
import cn.xxm.services.WeatherReportService;
import cn.xxm.vo.City;
import cn.xxm.vo.CityList;
import cn.xxm.vo.Weather;
import cn.xxm.vo.WeatherReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 16:20
 */
@Controller
@RequestMapping("/report")
public class WeatherReportController {

    @Autowired
    private WeatherReportService weatherReportService;

    @Autowired
    private CityClientService cityClientService;//Feign  声明接口的方式调用服务

    @GetMapping("/getWeather/{cityName}")
    @LoggerManage(description = "[查询天气预报]")
    public String getWeather(@PathVariable("cityName") String cityName , Model model){
        String title = "鸣哥的天气预报";
        // 具体查询城市的天气信息
        Weather weather = weatherReportService.getDataByCityName(cityName);
        // 需要城市列表
//        List<City> cityList =  weatherReportService.getCityList();
        List<City> cityList = cityClientService.getCityList();

        WeatherReportVo weatherReportVo = new WeatherReportVo(title,cityName,cityList,weather);

        model.addAttribute("weatherReportVo",weatherReportVo);
        return "weather/report";
    }
}
