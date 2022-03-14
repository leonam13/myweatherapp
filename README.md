#Leo Weather App - A Weather App made by Leo :D#

Architecture: MVVM + Repository Pattern

Data Sources:
Remote: Metaweather Service

What does the app do:
Using Fused Location API, once the user location is retrieved, a server request is made to find the correspondent ID
and right after a request to find the most recent weather data from that location.
The result is then displayed for the user.

How to run:
This app was designed for devices running android API 21 to 31.
The app was used using Google Fused Location API, so it's assuming the device must have Google Play Services installed.
Just open the project in android studio and left click on the "run" button.

Trade-Offs:
- Not implemented a local data source to save values on cache.
- Not handling SVG icons when using Glide.
- The permission denied flow can be improved.
- Improve how to get location, because in some devices fused api took a long time to return the coordinates.
- Write more tests for components below to ViewModel.
- Not implemented accessibility features.
- Not handling device orientation changes.

3rd party libraries:
- Jetpack navigation component and Safe args
- Retrofit for network layer
- Glide for loading icons
- Lottie for loading animation
- Hilt for Dependency Injection
- Dependencies for test as hamcrest, roboletric, espresso.

Used some base code from following sources:
For Location Settings:
https://developer.android.com/training/location/change-location-settings.html

For Location Updates:
https://developer.android.com/training/location/request-updates

Result class and LiveDataTestUtil from https://github.com/android/architecture-samples

Single live event
https://proandroiddev.com/singleliveevent-to-help-you-work-with-livedata-and-events-5ac519989c70