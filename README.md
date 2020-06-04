# Service Ticket Resolution System | SpringBoot
It's backend for Service Ticket Resolution System Application build using SpringBoot

Service Ticket Resolution System Application Requirements:
```
1.  Create a web application for service ticket resolution system at a customer care centre.
    There are two users – service engineer and end user. 
    The end user raises a ticket (or lodges a complaint in the system) for a particular problem. 
2.  Create a method to create a service ticket that has 
      a. Ticket ID
      b. Priority
      c. Start date
      d. Requested end date
      e. Status
      f. Service engineer
      g. customer
3.  Upon creation of the ticket, the system should automatically look for a service engineer who is not assigned any tickets and assign the ticket to him/her. 
    The service engineer table has the below:
      a. Id
      b. Name
      c. Area of expertise
      d. Total tickets worked on
      e. Current high priority ticket ID
4.   If there are no service engineers available without tickets, the method should look for those that have lower priority tickets and assign to the one who is working on the most recently created low priority ticket
5.   At this time, the status of the low priority ticket has to be changed to pending
6.   Create a method to report completion of a ticket by the service engineer
7.   Create a method to report statistics of avg time taken 
      f. Per severity
      g. Per engineer
8.   Create a method to report aging of open tickets. By aging we mean the number of days a ticket is open.
```
