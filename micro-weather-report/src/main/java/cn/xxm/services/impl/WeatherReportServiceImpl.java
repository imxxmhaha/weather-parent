package cn.xxm.services.impl;

import cn.xxm.constant.WeatherConstant;
import cn.xxm.service.BasicClientService;
import cn.xxm.services.WeatherReportService;
import cn.xxm.utils.JSONUtils;
import cn.xxm.vo.City;
import cn.xxm.vo.CityList;
import cn.xxm.vo.Weather;
import cn.xxm.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 16:11
 */
@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BasicClientService basicClientService;

    /**
     * 根据城市名称查询天气信息
     *
     * @param cityName
     * @return
     */
    @Override
    public Weather getDataByCityName(String cityName) {
//        WeatherResponse weatherResponse = restTemplate.getForObject(WeatherConstant.WEATHER_BASIC_URL + cityName, WeatherResponse.class);
        WeatherResponse weatherResponse = basicClientService.getWeather(cityName);
        return weatherResponse == null ? null : weatherResponse.getData();
    }

    /**
     * 获取城市列表
     *
     * @return
     */
    @Override
    public List<City> getCityList() {
        String string = restTemplate.getForObject(WeatherConstant.CITYLIST_BASIC_URL, String.class);
        List<City> cityList = JSONUtils.json2list(string, City.class);
        return cityList;
    }

}
