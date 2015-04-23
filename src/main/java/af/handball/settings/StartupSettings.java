package af.handball.settings;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import af.handball.repository.GameRepository;


@Component

public class StartupSettings{

	private Scheduler scheduler;
	
	@Autowired
	GameRepository gameRepository;
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("PRE DESTROY!!!!!");
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			System.err.println("couldn't shutdown the scheduler. SchedulerException: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
  @PostConstruct
  public void onApplicationEvent() {
    System.out.println("onApplicationEvent @PostConstruct");
    
    StdSchedulerFactory factory=new StdSchedulerFactory();
    Properties properties=new Properties();
    properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
    properties.setProperty("org.quartz.scheduler.instanceName","MatchScheduler");
    properties.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
    properties.setProperty("org.quartz.jobStore.driverDelegateClass","org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
    properties.setProperty("org.quartz.jobStore.dataSource", "schedulerDataSource");
   /* properties.setProperty("org.quartz.jobStore.tablePrefix", "_QRTZ");*/
    properties.setProperty("org.quartz.dataSource.schedulerDataSource.driver", "org.apache.commons.dbcp.BasicDataSource");
    properties.setProperty("org.quartz.dataSource.schedulerDataSource.URL", "jdbc:postgresql://localhost:5432/handball");
    properties.setProperty("org.quartz.dataSource.schedulerDataSource.user", "postgres");
    properties.setProperty("org.quartz.dataSource.schedulerDataSource.password", "003343adaja");

    /*properties.setProperty("org.quartz.scheduler.wrapJobExecutionInUserTransaction", "true");*/
    
    properties.setProperty("org.springframework.scheduling.quartz.SchedulerFactoryBean.autoStartup", "true");
    properties.setProperty("org.springframework.scheduling.quartz.SchedulerFactoryBean.applicationContextSchedulerContextKey", "applicationContext");
    
    
    properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
    properties.setProperty("org.quartz.threadPool.threadCount","10");
   /* A daemon thread is a thread, that does not prevent the JVM from exiting when the program finishes but the thread is still running. An example for a daemon thread is the garbage collection.*/
   
    /* properties.setProperty("org.quartz.threadPool.makeThreadsDaemons","true");
    properties.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon","true");*/
    properties.setProperty("dataSource", "dataSource");
    properties.setProperty("DataSource", "dataSource");
    try {
		factory.initialize(properties);
		scheduler=factory.getScheduler();
		System.out.println("StdScheduleFactory initialized for scheduler name: " + scheduler.getSchedulerName());
		scheduler.start();
		 for (String groupName : scheduler.getJobGroupNames()) {
			 
		     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
		 
			  String jobName = jobKey.getName();
			  String jobGroup = jobKey.getGroup();
			  
			  JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			  
			  //get job's trigger
			  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
			  Date nextFireTime = triggers.get(0).getNextFireTime(); 
			  
			 /* System.out.println("[jobName] : " + jobName + " [groupName] : "
						+ jobGroup + " - " + nextFireTime);*/
			
			  int matchId = (Integer)   scheduler.getJobDetail(jobKey).getJobDataMap().getInt("matchId");;
			  System.out.println("Found job with matchId: " + matchId);
			  
			  
			  boolean changeApplied = gameRepository.setMatchIsPlayed(false, matchId);
			  gameRepository.deleteMatchHighlights(matchId);
			  if (changeApplied) {
				  /*System.out.println("Applied change (matchIsPlayed = false) in matchId: " + matchId);*/
			  }
				 
			  
		 
			
		 
				
		 
			  }
		 
		    }

		
		System.out.println("Scheduler started.");
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  }
}