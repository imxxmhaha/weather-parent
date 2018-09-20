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
//        String corn = "0 25 0/2 * * ?";//每隔2小时执行一次20分开始
        String corn = "0 0 12 * * ?";//每天12点执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(corn);
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
