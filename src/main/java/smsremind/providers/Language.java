package smsremind.providers;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

import smsremind.models.Reminder;
import smsremind.providers.Config;

public class Language {
  static String workspaceId = Config.workspaceId;
  static String waUsername = Config.waUsername;
  static String waPassword = Config.waPassword;
  static Assistant service = new Assistant("2018-02-16", waUsername, waPassword);

  public static void processInput (Reminder reminder) {
    InputData input = new InputData.Builder(reminder.getBody()).build();
    Context context = new Context();

    context.put("timezone", Database.getTimezoneName(reminder));

    MessageOptions options = new MessageOptions.Builder(workspaceId)
      .input(input)
      .context(context)
      .build();
    MessageResponse assistantResponse = service.message(options).execute();

    reminder.setOutputText(assistantResponse.getOutput().getText().get(0));
    reminder.setReminderTime(assistantResponse.getContext().get("reminderTime").toString());
    reminder.setReminderText(assistantResponse.getContext().get("reminderText").toString());
  }

}
