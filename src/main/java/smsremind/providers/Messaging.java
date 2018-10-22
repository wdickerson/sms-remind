package smsremind.providers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;

import com.twilio.type.PhoneNumber;

import smsremind.models.Reminder;
import smsremind.providers.Config;

public class Messaging {
  static final String ACCOUNT_SID = Config.twilioAccountSid;
  static final String AUTH_TOKEN = Config.twilioAuthToken;
  static final String ORIGIN_PHONE_NUMBER = Config.twilioOriginNumber;

  public static void sendSms (String reminderText, String phoneNumber) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(ORIGIN_PHONE_NUMBER), reminderText).create();
    return;
  }

  public static void createResponse (Reminder reminder) {
    Body messageBody = new Body.Builder(reminder.getOutputText()).build();
    com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message.Builder().body(messageBody).build();
    MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
    reminder.setMessagingResponse(twiml);
    return;
  }

}
