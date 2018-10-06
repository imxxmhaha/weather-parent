package cn.xxm.service;

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
    WeatherResponse getDataByCityId(String cityId,String cityName);



    /**
     * 定时任务更新天气预报
     * @param cityId
     * @return
     */
    WeatherResponse syncWeatherByCityId(String cityId,String cityName);


}
