package smsremind.servlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import smsremind.workers.SendReminderWorker;

@WebListener
public class AppContextListener implements ServletContextListener {
  SendReminderWorker reminderWorker = null;

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Shut down the worker to send reminders minutely");
		reminderWorker.stop();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
    System.out.println("Start the worker to send reminders minutely");
    reminderWorker = new SendReminderWorker();
	}

}
