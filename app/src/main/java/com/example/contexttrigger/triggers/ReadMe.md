

# Triggers Directory


This directory contains triggers that have been defined for the application to run. 
All triggers must follow the `Trigger` interface. 

Triggers house their own logic and determine how they uniquely respond to information 
received from the data producers.

## Trigger Interface

The Trigger interface defines the methods that a trigger must implement. 

These methods include:

- shouldRunNotification(context: Context): A method that determines if the trigger 
  should run a notification based on the context provided.

- getNotificationTitle(): A method that returns the title of the notification to be displayed.

- getNotificationMessage(): A method that returns the message of the notification to be displayed.

- handle(): A method that defines how trigger would process new information received.


