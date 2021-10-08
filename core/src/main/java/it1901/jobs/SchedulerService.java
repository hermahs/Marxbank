package it1901.jobs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class SchedulerService {
    
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler=scheduler;
        init();
    }

    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            System.err.println("Scheduler could not start");
        }
    }

    public void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            System.err.println("Scheduler could not be shut down");
        }
    }

    public void scheduleTransactionJob() { //TODO
        try {
            JobDetail job = JobBuilder.newJob(TransactionJob.class)
                .withIdentity("transaction")
                .build();

            Trigger tr = TriggerBuilder.newTrigger()
                .withIdentity("transactionTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                    .simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever())
                .build();
            
            scheduler.scheduleJob(job, tr);

        } catch (SchedulerException e) {
            System.err.println("Could not schedule job");
        }        
    }
}
