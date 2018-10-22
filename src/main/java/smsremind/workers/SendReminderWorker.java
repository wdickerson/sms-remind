package smsremind.workers;

import java.util.Timer;
import java.util.TimerTask;
import smsremind.providers.Database;

public class SendReminderWorker {
  Timer timer = new Timer ();
  TimerTask workerTask = new TimerTask () {
    @Override
    public void run () {
      Database.sendReminders();
    }
  };

  public SendReminderWorker () {
    timer.scheduleAtFixedRate(workerTask, 0, 60000);
  }

  public void stop () {
    timer.cancel();
  }

}
