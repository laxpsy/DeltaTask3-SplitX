# DeltaTask3SplitX
### Tech-Stack
- Kotlin (Jetpack Compose)
- JavaScript(Express)
- mySQL

Was tested on Android OnePlus Nord CE using port-forwarding via Google Chrome and USB Debugging over WiFi.
`NodeMon` was used to accelerate the development process of the REST API. 

### Demonstration-Video
https://drive.google.com/file/d/1-Nznk1tZDkX7EvmGEFYzcpABUCniT93S/view?usp=drive_link

The glowing/animated border in the History section identifies the person who initated the Split.

### Known-Issues
- `SocketTimeoutException` might occurs sometimes when sending a request. An overall app crash has been prevented at each location with the use of a try-catch block. Is circumvented everytime by restarting the server. 
