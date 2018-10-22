# sms-remind
A personal sms reminder bot using Twilio and Watson Assistant

## How it works

Send an sms text like this to your twilio number  
`Remind me to stop by midtown lumber at 5pm`

Your reminder bot will respond  
`Sure, I'll remind you to stop by midtown lumber at 5:00PM, 10/22`

Then, at 5:00pm, you'll get this reminder  
`Don't forget to stop by midtown lumber`

## Pre-reqs
1. A Watson Assitant workspace  
2. A db2 instance (or another sql database instance)  
3. A Twilio sms account  
4. maven and a tomcat server  

## Running locally
1. Rename `ConfigSample.java` to `Config.java` and add your creds  
2. `mvn install`  
3. Run the `.war` file on a tomcat server (I do this within VSCode)  

You can point your browser to `http://localhost:9080/SmsRemind/` to confirm
that the app is running, but this is just a splash screen.

## TODO
1. Add the Watson Assistant workspace content to this repo  
2. Add the database schema to this repo  
3. Deal with timezones  
4. Build a portal to view/edit reminders  
