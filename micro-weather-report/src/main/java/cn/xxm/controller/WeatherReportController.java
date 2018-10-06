package cn.xxm.controller;

import cn.xxm.aop.logaop.LoggerManage;
import cn.xxm.service.CityClientService;
import cn.xxm.service.WeatherReportService;
import cn.xxm.vo.City;
import cn.xxm.vo.Weather;
import cn.xxm.vo.WeatherReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 16:20
 */
@Controller
public class WeatherReportController {

    @Autowired
    private WeatherReportService weatherReportService;

    @Autowired
    private CityClientService cityClientService;//Feign  声明接口的方式调用服务

    @GetMapping("/report/getWeather/{cityId}")
    @LoggerManage(description = "[查询天气预报]")
    public String getWeather(@PathVariable("cityId") String cityId , Model model){
        String title = "鸣哥的天气预报";
        // 需要城市列表
        List<City> cityList = cityClientService.getCityList();
        String  cityName = getCityName(cityId, cityList);

        // 具体查询城市的天气信息
        Weather weather = weatherReportService.getDataByCityId(cityId,cityName);

        WeatherReportVo weatherReportVo = new WeatherReportVo(title,cityName,cityId,cityList,weather);

        model.addAttribute("weatherReportVo",weatherReportVo);
        return "weather/report";
    }

    private String getCityName( String cityId, List<City> cityList) {
        String cityName = "";
        //从cityList中找出 cityId  对应的的cityName
        for(City city:cityList){
            if(city.getCityId().equals(cityId)){
                cityName = city.getCityName();
                break;
            }
        }
        return cityName;
    }


    @GetMapping("/")
    @LoggerManage(description = "[欢迎进入小鸣哥的天气预报]")
    public String getCityList(){
        String cityId = "101280101";
        return "redirect:/report/getWeather/"+cityId;
    }




}
