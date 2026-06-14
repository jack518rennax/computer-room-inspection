package com.patrol.task.config;

import com.patrol.task.job.PatrolTaskJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Quartz 定时任务配置
 *
 * @author patrol-team
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private static final String JOB_NAME = "patrolTaskJob";
    private static final String JOB_GROUP = "patrolTaskGroup";
    private static final String TRIGGER_NAME = "patrolTaskTrigger";
    private static final String TRIGGER_GROUP = "patrolTaskGroup";

    @Value("${patrol.task.cron:0 */1 * * * ?}")
    private String cronExpression;

    private final Scheduler scheduler;

    @Bean
    public JobDetail patrolTaskJobDetail() {
        return JobBuilder.newJob(PatrolTaskJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP)
                .withDescription("巡检任务调度 Job")
                .storeDurably()
                .build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void scheduleJobs() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(patrolTaskJobDetail())
                .withIdentity(TRIGGER_NAME, TRIGGER_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing())
                .build();

        if (!scheduler.checkExists(patrolTaskJobDetail().getKey())) {
            scheduler.scheduleJob(patrolTaskJobDetail(), trigger);
            log.info("Quartz 巡检任务调度已注册: cron={}", cronExpression);
        } else {
            scheduler.rescheduleJob(trigger.getKey(), trigger);
            log.info("Quartz 巡检任务调度已更新: cron={}", cronExpression);
        }
    }
}
