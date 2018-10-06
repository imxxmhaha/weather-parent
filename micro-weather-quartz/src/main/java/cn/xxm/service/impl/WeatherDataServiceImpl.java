package cn.xxm.service.impl;

import cn.xxm.constant.RedisConstant;
import cn.xxm.constant.WeatherConstant;
import cn.xxm.service.BasicClientService;
import cn.xxm.service.CityClientService;
import cn.xxm.service.WeatherDataService;
import cn.xxm.utils.JSONUtils;
import cn.xxm.vo.City;
import cn.xxm.vo.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xxm
 * @create 2018-08-30 21:21
 */
@Service
@Slf4j
public class WeatherDataServiceImpl implements WeatherDataService {


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private CityClientService cityClientService;

    @Autowired
    private BasicClientService basicClientService;

    /**
     * 根据城市cityName来同步天气
     *
     * @param cityName
     */
    @Override
    public synchronized void syncDataByCityId(String cityId, String cityName) {

        saveWeatherDataToRedis(cityId,cityName);

    }

    @Override
    public List<City> getCityList() {
        //restemplate的方式
//        String string = restTemplate.getForObject(WeatherConstant.CITYLIST_BASIC_URL, String.class);
//        List<City> cityList = JSONUtils.json2list(string, City.class);
        //feign的方式操作
        List<City> cityList = cityClientService.getCityList();
        return cityList;
    }


    private synchronized void saveWeatherDataToRedis(String cityId,String cityName) {
        WeatherResponse response = null;
        String uri = WeatherConstant.QUERY_WEATHER_URI + "city=";
        try {
            response = basicClientService.syncWeather(cityId,cityName);
            redisTemplate.boundValueOps(RedisConstant.WEATHER_PRIFIX + cityName).set(JSONUtils.obj2json(response), 60 * 60 * 24, TimeUnit.SECONDS);
        } catch (RestClientException e) {
            log.error("调用第三方接口天气接口查询失败,接口地址:{}", uri);
            log.error("重新查询:{}的天气接口,接口地址:{}", cityName, uri);
            saveWeatherDataToRedis(cityId,cityName);
        }

    }

}
