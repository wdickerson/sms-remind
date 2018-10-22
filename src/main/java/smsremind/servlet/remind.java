package smsremind.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.TwiMLException;

import smsremind.models.Reminder;

/**
 * Servlet implementation class remind
 */
@WebServlet("/remind")
public class remind extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getParameter("Body");
        String fromNumber = request.getParameter("From");
        Reminder reminder = new Reminder(body, fromNumber);

        System.out.println("heyohh new !@!@!@!");
        System.out.println("@!@!");

        // Deal with the language
        reminder.processInput();

        // Deal with the database
        reminder.insertReminder();

        // Deal with twilio
        reminder.createResponse();

        response.setContentType("application/xml");
        try {
            response.getWriter().print(reminder.getMessagingResponse().toXml());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }

}
