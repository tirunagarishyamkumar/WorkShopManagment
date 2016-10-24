package com.btcc.wsm.util;


import com.btcc.wsm.service.SchedulerService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import org.quartz.SchedulerException;

/**
 * Created by siva on 24/10/2016.
 */
public class QuartzListener implements ServletContextListener {
    org.quartz.Scheduler scheduler = null;


    public void contextInitialized(ServletContextEvent servletContext) {
        System.out.println("Context Initialized");

        // new PropertiesConfig();

        SchedulerService schedulerService = ApplicationContextHolder.getContext().getBean(SchedulerService.class);
        List<com.btcc.wsm.model.Scheduler> schedulerList = null;//schedulerService.findAll(); later needs to be change
        int listSize = 0;//schedulerList.size();

        if (listSize > 0) {

            for (int i = 0; i < listSize; i++) {
                        com.btcc.wsm.model.Scheduler jobScheduler = schedulerList.get(i);
                        EmailQuartzScheduler.scheduleQuartzJob(jobScheduler);


                }
            }

        }




    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println("Context Destroyed");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
