package smsremind.providers;
import java.sql.*; 

import smsremind.models.Reminder;
import smsremind.providers.Messaging;
import smsremind.providers.Config;

public class Database {
  static String url = Config.dbUrl;
  static String user = Config.dbUsername;
  static String password = Config.dbPassword;

  public static void sendReminders () {
    Statement stmt;
    ResultSet rs;
    Connection con;

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");
    }
    catch (ClassNotFoundException e) {
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }

    String query = "SELECT reminder_text, phone_number"
    + " FROM FINAL TABLE (" 
    + "  UPDATE reminder AS r "
    + "  SET r.reminder_sent = true"
    + "  WHERE r.reminder_sent = false"
    + "  AND r.reminder_time < (current timestamp + 1 MINUTES +"
    + "   (SELECT u.timezone_offset FROM user u WHERE u.phone_number = r.phone_number) HOURS"
    + "  )"
    + ")";

    try {
      // connect and execute sql
      con = DriverManager.getConnection (url, user, password);
      stmt = con.createStatement();
      rs = stmt.executeQuery(query);

      // work on the results set
      while (rs.next()) {
        Messaging.sendSms(rs.getString("reminder_text"), rs.getString("phone_number"));
      }

      // Close everything
      rs.close();
      stmt.close();
      con.close();

    } catch(SQLException ex) {
      System.err.println ("Error msg: " + ex.getMessage());
      System.err.println ("SQLSTATE: " + ex.getSQLState());
      System.err.println ("Error code: " + ex.getErrorCode());
    }
  }

  public static void insertReminder (Reminder reminder) {
    PreparedStatement preparedStmt;
    Connection con;
    final String columns = "(phone_number, reminder_text, reminder_time)";
    final String preparedQuery = "INSERT INTO reminder " + columns + " VALUES (?, ?, ?)";

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");
    }
    catch (ClassNotFoundException e) {
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }

    try {
      // connect and execute sql
      con = DriverManager.getConnection (url, user, password);
      preparedStmt = con.prepareStatement(preparedQuery);
      preparedStmt.setString(1, reminder.getPhoneNumber());
      preparedStmt.setString(2, reminder.getReminderText());
      preparedStmt.setString(3, reminder.getReminderTime());
      preparedStmt.execute();

      // Close everything
      preparedStmt.close();
      con.close();

    }
    catch(SQLException ex) {
      System.err.println ("Error msg: " + ex.getMessage());
      System.err.println ("SQLSTATE: " + ex.getSQLState());
      System.err.println ("Error code: " + ex.getErrorCode());
      ex.printStackTrace();
      ex = ex.getNextException(); // For drivers that support chained exceptions
    }
  }

  public static String getTimezoneName(Reminder reminder) {
    Statement stmt;
    ResultSet rs;
    Connection con;
    String timezoneName = "America/New_York";

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");
    }
    catch (ClassNotFoundException e) {
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }

    String query = "SELECT timezone_name"
    + " FROM user" 
    + " WHERE phone_number = '" + reminder.getPhoneNumber() + "'";

    try {
      // connect and execute sql
      con = DriverManager.getConnection (url, user, password);
      stmt = con.createStatement();
      rs = stmt.executeQuery(query);

      // work on the results set
      if (rs.next()) {
        timezoneName = rs.getString("timezone_name");
      }

      // Close everything
      rs.close();
      stmt.close();
      con.close();      

    } catch(SQLException ex) {
      System.err.println ("Error msg: " + ex.getMessage());
      System.err.println ("SQLSTATE: " + ex.getSQLState());
      System.err.println ("Error code: " + ex.getErrorCode());
    }
    return timezoneName;
  }

}
