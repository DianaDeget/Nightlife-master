# Nightlife App

Project by Diana Andreea Deget
github link: https://github.com/DianaDeget/Nightlife-master
project demo video link:  https://www.youtube.com/watch?v=qtLRjkgQyww ( Watch at 1.5x speed :) (or https://youtu.be/rd6b6fptISo in case the first one isn't working )

## Idea / What to expect:

The “Nightlife” app focuses on providing information to its users about specific gatherings [parties] organised in their area as well as submitting their own event in the app for others to see, thus creating a non-centralised approach to the re-opening of the social life, where anyone can be a host.
Some of the basic functionalities of the app are:

- The user can choose the radius and the timeframe for the specific search, being able to filter the events that suit its schedule.
- The user can also filter the events based on the genre of music / style of party [bonding, music, costumes, so on...]
- The user can set itself the status regarding a party as such:
    - Joining: The user is sure to join
    - Maybe: The user might be able to join / Is not sure
    - Not going*: The user is sure not to join
    *the “Not going” status is only selectable by a user that has been invited to the specific event.
- Any user can create an event / gathering and can also invite other users using their mobile phone / username or mark it as a public event.

## Requirements:

- **Critical**
    - [ x ] The guest can create an account so that he can become a user.
    - [ x ] The user can create an event so that it can be visible to others. 
    - [ x ] The guest can access an event only if he has the invitation link for the event.
    - [ x ] The guest can log in so that he can see events without the need of a link. 
    - The user can search for an event so that he can find information about it.
    - The user can filter the search results by a date/time interval and by radius.
    - [ x ] The user can add information about his event so that others can decide their attendance.
    - The user can see the total number of people joining, maybe joining or not joining so that they know what to expect.
- **High Priority**
    - [ x ] (AUTO) The user can edit the status of his event to [Planning], [Preparing], [Live] and [Over] so that people know how to schedule their attendance.
    - The user can add a profile picture for his account so that others can recognise him better.
    - The user can invite other users to its event so that they can see information about it or assign themselves a status regarding their attendance.
    - The user can assign itself a status regarding their attendance [Joining / Maybe] on all the events that are visible to them.
    - In addition to the previous requirement, the user can assign itself the [Not Joining] status regarding their attendance on those events that he is invited to.
- **Low Priority**
    - [ x ] The user can delete an event so that it is no longer visible to the other users.
    - The user can make an event they organise reccuring so that they do not need to create multiple events if there are more in a row.
    - The user can add the type of event so that other users can search for their specific “taste”.


## The process of creation
 Starting with the Basics, the project was created by following the steps from itslearning. Using the TextView, buttons and the layouts was the seed of our program.
As it can already be seen in the res folder>layout, the app was designed to be responsive. By following the Navigation step, the app evolved from a single activity app to a multiple activity app.
By opening the app/looking through the youtube presentation, it can pe seen that the app has multiple activities. The user is able to log in by using an username and a password. After typing them both in the app, the button LOGIN can be pressed.
From here, another activity starts in which the user is able to either go through settings, logout or see the events. By clicking the events button, the user is sent to another interface.
The events are created by other users and contain a brief description of the event, an address, the starting and ending time and it offers the user the possibility to see the exact address on maps.
Another thing that the user can do from this activity is to press the button "+". From there, he/she is redirected to the create event page, in which the user itself can create it's own event/party as well as remove the created event afterwards if it is the case.
In case the delete event option was selected by mistake, the system send notifications to the users in order to give him a second chance of the action. (see >MainActivity>>deleteEvent method).
By pressing the create Event button, the user is asked to type the starting and ending date and time, a title, location and coordinatess >>See Event class and EventDate class from model. More infromations about the event can be seen by checking the status of the event.
Each event can have a status of "planning", "preparing", "live" or "over" (check EventStatus class). That way, other users know what to expect. Users usually have an username, password, email and phone number (plus id), but they don't have to have an account in order to see details of an party. 
If they have received a link of one of the parties already posted on the app, they can use that link in order to see the location, name, time etc. of the party, by being automatically sent after they put the url in the bar, on the activity with the specific party.
No other parties can be visible in this circumstance for the user, except the one that he was invited to. 
By using the firebase , multiple accounts were created. In order to simplify the code, more resources for strings and colors were created. The app has a specific theme stored in the themes directory from res. By looking at res directory, the navigation of the app can be followed >nav_graph.xml.
Fragments have been used in the app in order to make sure that they can be combined in a single activity and reused afterwards in multiple other activities. 
For having a more organized activity, snackbars have been used instead of toast functions. An example can be seen in >Home Fragment from UI. 
 
