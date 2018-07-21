# SafeWalk

SafeWalk is a student-built app that aims to provide safety for college students walking home late at night. 
SafeWalk allows users to find another person to walk home with at night when their only other option is to walk home alone. 
When the user enters the app, the app finds their location and allows them to drop a pin on the place they are headed. 
Their name, current location, and destination are sent to a Google Cloud server which returns another user who had searched for a walking 
buddy, and sends both users the other’s information. It allows the user to cancel the request and look for another user and would also 
ideally implement a messaging system between the matched users and a 911 button using the SafeTrek API, which the user could keep open 
in the app until they have gotten home. The application was developed using Android Studio. 

## Requirements

Android Studio 3.0.1 or higher

## Installation 

```
git clone https://github.com/vuppalli/SafeWalk.git
cd SafeWalk
open -a /Applications/Android\ Studio.app ~/SafeWalk
```
Build the project and run on any simulator or device with Android 5.0 or higher

## Built With

- [Android Studio](https://developer.android.com/studio/) - app development software
- [Google Cloud Platform](https://cloud.google.com/) - used to write HTTP functions and access/save data in cloud
- [Volley](https://developer.android.com/training/volley/) - HTTP library for Android Studio
- [Google Maps Platform](https://cloud.google.com/maps-platform/) - finding and locating users

## Contributors

- Vismita Uppalli
- Sindhu Ranga
- Stephanie Zablocki

## Note

First created as a project for HackUVA and is being futher developed with a UVA professor to deploy at the university.
