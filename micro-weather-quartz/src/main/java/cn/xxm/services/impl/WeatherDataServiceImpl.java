package cn.xxm.services.impl;

import cn.xxm.constant.RedisConstant;
import cn.xxm.constant.WeatherConstant;
import cn.xxm.services.WeatherDataService;
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



    /**
     * 根据城市ID来同步天气
     * @param cityName
     */
    @Override
    public void  syncDataByCityName(String cityName) {
        saveWeatherDataToRedis(cityName);

    }

    @Override
    public List<City> getCityList() {
        String string = restTemplate.getForObject(WeatherConstant.CITYLIST_BASIC_URL, String.class);
        List<City> cityList = JSONUtils.json2list(string, City.class);
        return cityList;
    }


    private synchronized void saveWeatherDataToRedis(String cityName){
        WeatherResponse response = null;
        String uri = WeatherConstant.QUERY_WEATHER_URI +"city="+cityName;
        try {
            response = restTemplate.getForObject(uri, WeatherResponse.class);
            redisTemplate.boundValueOps(RedisConstant.WEATHER_PRIFIX+cityName).set(JSONUtils.obj2json(response),60*60*24,TimeUnit.SECONDS);
        } catch (RestClientException e) {
            log.error("调用第三方接口天气接口查询失败,接口地址:{}",uri);
            log.error("重新查询:{}的天气接口,接口地址:{}",cityName,uri);
            saveWeatherDataToRedis(cityName);
        }

    }

}
