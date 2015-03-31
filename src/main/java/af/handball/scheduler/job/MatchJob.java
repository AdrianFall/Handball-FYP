package af.handball.scheduler.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import af.handball.repository.MatchRepository;
import af.handball.scheduler.bean.AnotherBean;

public class MatchJob implements Job {
	

	
	@Autowired
	MatchRepository matchRepository;
	
   private AnotherBean anotherBean; 
     
 
    public void setAnotherBean(AnotherBean anotherBean) {
        this.anotherBean = anotherBean;
    }


	@Override
	public void execute(JobExecutionContext cntxt) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		long matchTime = cntxt.getJobDetail().getJobDataMap().getLong("matchTime");
		int matchId = cntxt.getJobDetail().getJobDataMap().getInt("matchId");
		Date matchDate = new Date(matchTime);
		
		Calendar calendar = Calendar.getInstance();
		
		System.out.println("Execute(v12) from job key: " + cntxt.getJobDetail().getKey() + ", Trigger key: " + cntxt.getTrigger().getKey() + ". Match id: " +  matchId + ". Scheduled time: " + matchDate.toString() + ". Executing at time: " + calendar.getTime().toString() + ".");
		
		try {
			Map<String,Object> settingsMap = matchRepository.initializeMatch(matchId);
			
			boolean initialized = (Boolean) settingsMap.get("initialized");
			if (!initialized) {
				rescheduleJob(10, matchId, cntxt);
				 
			} else { // initialized
			
				final int matchDurationInSeconds = (Integer) settingsMap.get("matchDurationInSeconds");
				
				final int numberOfUpdates = (Integer) settingsMap.get("numberOfUpdates");
				
				final int delayInSeconds = matchDurationInSeconds / numberOfUpdates; 
				try {

					for (int i = 0; i < numberOfUpdates; i++) {
						
						try {
							System.out.println("Performing update number = " + (i+1));
							boolean updated = matchRepository.updateMatch();
							if (!updated)
								System.err.println("Couldn't perform update number: " + (i+1) + ". On match id: " + matchId);
							Thread.sleep(delayInSeconds * 1000);
							
							
							
						} catch (InterruptedException e) {
							System.err.println("MatchRepository failed at sleeping thread for match (" + matchId  + ") InterruptedException: " + e.getLocalizedMessage());
							e.printStackTrace();
						}
					} // End looping for updates
				
					boolean generated = matchRepository.generateMatchOutcome();
					if (generated) 
						System.out.println("Match outcome generated for match (" + matchId + "). Finishing the job (" + cntxt.getJobDetail().getKey() + ").");
					else 
						System.out.println("Match outcome couldn't be generated.");
					} catch (Exception e) {
						System.err.println("Couldn't generate match. Exception: " + e.getLocalizedMessage());
						rescheduleJob(1, matchId, cntxt);
					}
				
			} // END else (initialized)
			
		} catch (Exception e) {
			int rescheduleTimeInMinutes = 1;
			System.err.println("SEVERE: Couldn't initialize match id: " + matchId + ". Rescheduling in " + rescheduleTimeInMinutes + " minute(s).");
			/*throw new JobExecutionException("Exception upon initializing match id: " + matchId, e, false);*/
			rescheduleJob(rescheduleTimeInMinutes, matchId, cntxt);
		}
		
		
		
	} // END execute()
	
	private boolean rescheduleJob(int minutes, int matchId, JobExecutionContext cntxt) {
		boolean rescheduled = false;
		System.out.println("Rescheduling job for match id: " + matchId);
		// Reschedule the job
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutes);
		
		Trigger trigger = cntxt.getTrigger();
		
		  Trigger newTrigger = TriggerBuilder
		    		.newTrigger()
		    		/*.withIdentity(trigger.getKey().toString(), "group1")*/
		    		.startAt(calendar.getTime())
		    		.forJob(cntxt.getJobDetail().getKey())
		    		.build();
		  
		  try {
			cntxt.getScheduler().rescheduleJob(trigger.getKey(), newTrigger);
			 System.out.println("Rescheduled job (" + cntxt.getJobDetail().getKey() + ") at time: " + calendar.getTime().toString());
		} catch (SchedulerException e) {
			System.err.println("Re-Scheduling error, for job (" + cntxt.getJobDetail().getKey() + ") at time: " + calendar.getTime().toString() + ". SchedulerException " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return rescheduled;
	}
	
}