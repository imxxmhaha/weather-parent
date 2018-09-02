package cn.xxm.services;

import cn.xxm.vo.City;
import cn.xxm.vo.CityList;
import cn.xxm.vo.Weather;

import java.util.List;

/**
 * @author xxm
 * @create 2018-09-01 16:10
 */
public interface WeatherReportService {
    /**
     * 根据城市名称查询天气信息
     * @param cityName
     * @return
     */
    Weather getDataByCityName(String cityName);

    /**
     * 城市列表
     * @return
     */
    List<City> getCityList();
}
