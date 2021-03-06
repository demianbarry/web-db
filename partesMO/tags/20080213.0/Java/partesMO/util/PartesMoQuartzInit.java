package partesMO.util;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

import com.salmonllc.util.MessageLog;

/**
 * Set the jobs and start the scheduler. 
 * See http://www.opensymphony.com/quartz/api/ for details.
 * 
 * @author Francisco Ezequiel Paez
 * 
 */
public class PartesMoQuartzInit extends HttpServlet implements Servlet {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -944367551302525151L;
	
	@Override
	public void init() throws ServletException {	
		super.init();
		
		System.out.println("PatesMO: Quartz initializing...");
				
		try {
			StdSchedulerFactory factory = (StdSchedulerFactory) 
		      getServletContext().getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
			
			JobDetail generaResumenRelojDetail = new JobDetail(
					"generaResumenRelojQuartzJob", null, GeneraResumenRelojQuartzJob.class);
			// Launch the trigger everyday at 1 am
			Trigger generaResumenRelojTrigger = TriggerUtils.makeDailyTrigger(1, 2);			
			generaResumenRelojTrigger.setName("generaResumenRelojTrigger");
			
			JobDetail getFichadasFromTangoDetail = new JobDetail(
					"getFichadasFromTangoQuartzJob", null, GetFichadasQuartzJob.class);
			Trigger getFichadasFromTangoTrigger = TriggerUtils.makeDailyTrigger(1, 0);
			getFichadasFromTangoTrigger.setName("getFichadasFromTangoTrigger");
			
			JobDetail liquidarPeriodoJobDetail = new JobDetail(
					"liquidarPeriodoQuartzJob", null, LiquidarPeriodoQuartzJob.class);
			// Launch the trigger the 15th day of each month at 1 am
			Trigger liquidarPeriodoTrigger = TriggerUtils.makeMonthlyTrigger(15,
					1, 15);			
			liquidarPeriodoTrigger.setName("liquidarPeriodoTrigger");
			
			Scheduler scheduler = factory.getScheduler();
			scheduler.scheduleJob(generaResumenRelojDetail, generaResumenRelojTrigger);
			scheduler.scheduleJob(getFichadasFromTangoDetail, getFichadasFromTangoTrigger);
			
			scheduler.start();
			
			System.out.println("PartesMO: Quartz start successful!");
		} catch (SchedulerException e) {
			MessageLog.writeErrorMessage(e, null);
			System.out.println("PartesMO: Quartz initialization error");
		}

	}
	
}
