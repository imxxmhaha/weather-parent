package cn.xxm.config;

import cn.xxm.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxm
 * @create 2018-08-31 23:13
 */
@Configuration
public class QuartzConfig {


    /**
     *  配置类需要两个Bean
     *  JobDetail  与  Trigger
     * @return
     */

    //JobDetail
    @Bean
    public JobDetail weatherDataSyncJobDetail(){
       return JobBuilder.newJob(WeatherDataSyncJob.class)
               .withIdentity("weatherDataSyncJob").storeDurably().build();
    }

    //Trigger
    @Bean
    public Trigger weatherDataSyncTrigger(){
//        String corn = "0/3 * * * * ?"; //每隔三秒执行一次
        String corn = "0 05 6/8 * * ?";//每隔8小时执行一次20分开始
//        String corn = "0 0 10 * * ?";//每天10点执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(corn);
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
