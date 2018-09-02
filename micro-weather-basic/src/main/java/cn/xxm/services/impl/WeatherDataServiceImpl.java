package cn.xxm.services.impl;

import cn.xxm.constant.RedisConstant;
import cn.xxm.constant.WeatherConstant;
import cn.xxm.services.WeatherDataService;
import cn.xxm.utils.JSONUtils;
import cn.xxm.vo.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    @Override
    public WeatherResponse getDataByCityName(String cityName) {

        String uri = WeatherConstant.QUERY_WEATHER_URI +"city="+cityName;
        WeatherResponse weatherResponse = getWeatherResponse(uri,cityName);
        return weatherResponse;
    }




    private WeatherResponse getWeatherResponse(String uri,String cityName) {

        WeatherResponse response = null;
        if(redisTemplate.hasKey(RedisConstant.WEATHER_PRIFIX+cityName)){
            //redis中  有数据  直接返回
            String weatherString = redisTemplate.boundValueOps(RedisConstant.WEATHER_PRIFIX+cityName).get().toString();
            try {
                 response = JSONUtils.json2pojo(weatherString,WeatherResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("将redis中"+weatherString+"转为WeatherResponse对象异常");
            }
        }else{
            response = restTemplate.getForObject(uri, WeatherResponse.class);
            redisTemplate.boundValueOps(RedisConstant.WEATHER_PRIFIX+cityName).set(JSONUtils.obj2json(response),1800,TimeUnit.SECONDS);
        }

        return response;
    }
}
