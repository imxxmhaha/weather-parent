package cn.xxm.services;

import cn.xxm.vo.City;
import cn.xxm.vo.WeatherResponse;
import lombok.Synchronized;

import java.util.List;

/**
 * @author xxm
 * @create 2018-08-30 21:19
 */
public interface WeatherDataService {



    /**
     * 根据城市ID来同步天气
     * @param cityName
     */
    void  syncDataByCityName(String cityName);

    /**
     * 调用micro-weather-basic  获取城市列表的服务  获取cityList
     * @return
     */
    List<City> getCityList();
}
