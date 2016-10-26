package com.btcc.wsm.util;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by siva on 24/10/2016.
 */
public class EmailQuartzScheduler {

    private static Logger log = LogManager.getLogger(EmailQuartzScheduler.class);

    public static boolean scheduleQuartzJob(com.btcc.wsm.model.Scheduler scheduler) {

        log.info("EmailScheduler : scheduleJob method scheduling quartz job started for job" + scheduler);

        boolean results = false;
        String cronExpression = null;//scheduler.getCronExpression();

        try {

            JobDetail jobDetail = JobBuilder.newJob(EmailScheduler.class).withIdentity(Integer.toString(111), "EPGGroup").build();

            assert cronExpression != null;
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(" EmailTrigger/" + 111, "WSMGroup").withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();

            org.quartz.Scheduler sch = new StdSchedulerFactory().getScheduler();
            sch.start();
            sch.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {

            log.error("EmailScheduler Error : scheduleJob unable to start the job ", e);
            return false;
        }
        log.info("EmailScheduler : Scheduled quartz job for scheduler time " + 111 + "and cron expression is " + cronExpression);

        return false;
    }

    public static boolean unScheduleQuartzJob(com.btcc.wsm.model.Scheduler scheduler) {

        boolean results = false;

        log.info("EmailScheduler : un schedule Job method un scheduling quartz job started for job");
        try {
            org.quartz.Scheduler sch = new StdSchedulerFactory().getScheduler();
            results = sch.unscheduleJob(new TriggerKey(" EmailTrigger/" + 111, "EPGGroup"));
            log.info("HouseKeepingQuartzScheduler : Unscheduled job results  " + results);
        } catch (SchedulerException e) {
            log.error("EmailScheduler Error : SchedulerException unable to un schedule the job ", e);
        }

        log.info("EmailScheduler : scheduleJob method un scheduling quartz job completed for job ");
        return results;

    }

    public static boolean reScheduleQuartzJob(com.btcc.wsm.model.Scheduler scheduler) {

        boolean results = false;
        String cronExpression = null;//scheduler.getCronExpression();

        try {
            assert cronExpression != null;
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(" EmailTrigger/" + 111, "EPGGroup").withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();

            org.quartz.Scheduler sch = new StdSchedulerFactory().getScheduler();
            // tell the scheduler to remove the old trigger with the given key,
            // and
            // put the new one in its place
            sch.rescheduleJob(new TriggerKey(" EmailTrigger/" + 111, "EPGGroup"), trigger);
            results = true;

        } catch (SchedulerException e) {

            log.error("EmailScheduler : SchedulerException unable to update and schedule job ", e);
            return false;

        }
        return true;
    }
}
