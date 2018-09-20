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
                 log.info("从redis中查询到的response的data数据是: {}",response.getData());
                 //如果redis中的data为null 删除redis中的key  重新访问api接口
                if(null == response.getData()){
                    log.info("redis中数据异常,删除之,重新请求,再缓存到redis");
                    redisTemplate.delete(RedisConstant.WEATHER_PRIFIX+cityName);
                    response = restTemplate.getForObject(uri, WeatherResponse.class);
                    redisTemplate.boundValueOps(RedisConstant.WEATHER_PRIFIX+cityName).set(JSONUtils.obj2json(response),1800,TimeUnit.SECONDS);
                }
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
