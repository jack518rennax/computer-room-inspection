package com.patrol.task.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.patrol.task.entity.PatrolTask;
import com.patrol.task.mapper.PatrolTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 巡检任务调度 Job
 * <p>
 * 每轮执行时扫描状态为"运行中"的巡检任务并记录日志。
 *
 * @author patrol-team
 */
@Slf4j
public class PatrolTaskJob extends QuartzJobBean {

    @Autowired
    private PatrolTaskMapper patrolTaskMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("=== Quartz 巡检任务调度开始 | trigger: {} | fireTime: {} ===",
                context.getTrigger().getKey(),
                context.getFireTime());

        try {
            // 扫描状态为"运行中"的巡检任务
            List<PatrolTask> runningTasks = patrolTaskMapper.selectList(
                    new LambdaQueryWrapper<PatrolTask>()
                            .eq(PatrolTask::getStatus, 1));

            log.info("扫描到 {} 个运行中的巡检任务", runningTasks.size());

            for (PatrolTask task : runningTasks) {
                log.info("  └─ 任务: id={}, name={}, locationId={}, cron={}",
                        task.getId(),
                        task.getName(),
                        task.getLocationId(),
                        task.getCronExpression());
            }

            if (runningTasks.isEmpty()) {
                log.info("  当前无待执行的巡检任务");
            }

        } catch (Exception e) {
            log.error("巡检任务调度执行异常", e);
            throw new JobExecutionException("巡检任务调度执行异常", e);
        }

        log.info("=== Quartz 巡检任务调度结束 | nextFireTime: {} ===",
                context.getNextFireTime());
    }
}
