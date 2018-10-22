package smsremind.models;

import com.twilio.twiml.MessagingResponse;
import smsremind.providers.Database;
import smsremind.providers.Language;
import smsremind.providers.Messaging;

public class Reminder {
  String reminderText;
  String reminderTime;
  String body;
  String phoneNumber;
  String outputText;
  MessagingResponse messagingResponse;

  public Reminder (String body, String phoneNumber) {
    this.body = body;
    this.phoneNumber = phoneNumber;
  }

  public void setReminderText (String reminderText) {
    this.reminderText = reminderText;
    return;
  }

  public void setReminderTime (String reminderTime) {
    this.reminderTime = reminderTime;
    return;
  }

  public String getBody () {
    return this.body;
  }

  public String getPhoneNumber () {
    return this.phoneNumber;
  }

  public String getReminderTime () {
    return this.reminderTime;
  }

  public String getReminderText () {
    return this.reminderText;
  }

  public String getOutputText () {
    return this.outputText;
  }

  public MessagingResponse getMessagingResponse () {
    return this.messagingResponse;
  }

  public void setOutputText (String outputText) {
    this.outputText = outputText;
    return;
  }

  public void setMessagingResponse (MessagingResponse twiml) {
    this.messagingResponse = twiml;
    return;
  }

  public String getTimezoneName() {
    return Database.getTimezoneName(this);
  }

  public void processInput() {
    Language.processInput(this);
    return;
  }

  public void insertReminder() {
    Database.insertReminder(this);
    return;
  }

  public void createResponse() {
    Messaging.createResponse(this);
    return;
  }

}
