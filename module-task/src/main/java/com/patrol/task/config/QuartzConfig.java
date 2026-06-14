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
 * <p>
 * 注册巡检任务调度 JobDetail 和 Trigger，
 * cron 表达式通过 application.yml 中的 patrol.task.cron 配置。
 *
 * @author patrol-team
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    /** Job 标识 */
    private static final String JOB_NAME = "patrolTaskJob";
    private static final String JOB_GROUP = "patrolTaskGroup";
    /** Trigger 标识 */
    private static final String TRIGGER_NAME = "patrolTaskTrigger";
    private static final String TRIGGER_GROUP = "patrolTaskGroup";

    /**
     * Cron 表达式，可通过 application.yml 覆盖。
     * 默认：每 1 分钟执行一次（Quartz 6 字段 cron 格式）
     */
    @Value("${patrol.task.cron:0 */1 * * * ?}")
    private String cronExpression;

    /**
     * Scheduler 由 Spring Boot QuartzAutoConfiguration 自动创建并注入
     */
    private final Scheduler scheduler;

    /**
     * 巡检任务 JobDetail
     */
    @Bean
    public JobDetail patrolTaskJobDetail() {
        return JobBuilder.newJob(PatrolTaskJob.class)
                .withIdentity(JOB_NAME, JOB_GROUP)
                .withDescription("巡检任务调度 Job — 扫描运行中的巡检任务并记录日志")
                .storeDurably()
                .build();
    }

    /**
     * 应用启动完成后，注册 JobDetail 和 Trigger 到 Scheduler。
     */
    @EventListener(ApplicationReadyEvent.class)
    public void scheduleJobs() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(patrolTaskJobDetail())
                .withIdentity(TRIGGER_NAME, TRIGGER_GROUP)
                .withDescription("巡检任务调度 Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing())
                .build();

        if (!scheduler.checkExists(patrolTaskJobDetail().getKey())) {
            scheduler.scheduleJob(patrolTaskJobDetail(), trigger);
            log.info("=== Quartz 巡检任务调度已注册 ===");
            log.info("  Job:       {}.{}", JOB_GROUP, JOB_NAME);
            log.info("  Trigger:   {}.{}", TRIGGER_GROUP, TRIGGER_NAME);
            log.info("  Cron:      {}", cronExpression);
        } else {
            // Job 已存在，仅更新 Trigger（支持热更新 cron）
            scheduler.rescheduleJob(trigger.getKey(), trigger);
            log.info("=== Quartz 巡检任务调度 Trigger 已更新 ===");
            log.info("  Cron:      {}", cronExpression);
        }
    }
}
