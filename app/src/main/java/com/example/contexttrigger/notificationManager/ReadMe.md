
# NotificationManager



The NotificationManager package contains code for managing notifications in an Android app. 

The NotificationManagerI class extends the Notification class and is responsible for running notifications based on triggers.

The `runRequiredNotifications` method is used to check if the app passes basic notification rules 

before running notifications based on triggers. 

The basic notification rules include:

- Checking if the user has allowed notifications in the app
- Checking if it is not night time
- Checking if the last notification was far enough in time


The `didUserAllowNotification` method uses the user's shared preferences to check 
if they have allowed notifications in the app. 

The `isNotNightTime` method checks if the current time is not night time, and the

`lastNotificationFarEnough` method checks if the last notification was far enough in time.

The `sameMessageNotSentRecently` method checks if the same message has been sent recently. 

If the same message has been sent within the last 45 minutes, it will not be sent again.

If the trigger should run a notification, the runRequiredNotifications method calls the 

`fireEvent` method from the Notification class to send the notification to the user.



Overall, the NotificationManagerI class takes into consideration the user's notification preferences, 


time of day, and frequency of notifications to manage notifications effectively in the app.





