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
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0,12 1/1 * ?");
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
