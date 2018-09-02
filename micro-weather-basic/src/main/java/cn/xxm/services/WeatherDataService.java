package cn.xxm.services;

import cn.xxm.vo.WeatherResponse;

/**
 * @author xxm
 * @create 2018-08-30 21:19
 */
public interface WeatherDataService {
    /**
     * 根据城市ID查询天气数据
     *
     * @param cityId
     * @return
     */
//    WeatherResponse getDataByCityId(String cityId);


    /**
     * 根据城市名称查询天气数据
     *
     * @param cityName
     * @return
     */
    WeatherResponse getDataByCityName(String cityName);
}
