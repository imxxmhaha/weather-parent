package cn.xxm.job;

import cn.xxm.service.WeatherDataService;
import cn.xxm.vo.City;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author xxm
 * @create 2018-08-31 23:11
 */
@Slf4j
public class WeatherDataSyncJob extends QuartzJobBean {

    @Autowired
    private WeatherDataService weatherDataService;


    @Override
    protected synchronized void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Wheather Data Sync Job Start!");
        List<City> cityList = null;
        // 获取城市ID列表
        try {
            cityList =  weatherDataService.getCityList();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取citylist失败");
        }

        //遍历城市ID获取天气
        if (null != cityList && cityList.size()>0){
            for (City city : cityList){
                String cityName = city.getCityName();
                String cityId = city.getCityId();
                log.info("Weather Data Sync job , cityName:{}",cityName);
                try {
                    Thread.currentThread().sleep(5000);
                    weatherDataService.syncDataByCityId(cityId,cityName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("Wheather Data Sync Job End!");
    }
}
