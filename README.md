# Task Management Application

## Description
The purpose of this assignment is to show how to use Jetpack Compose. [Download APK](https://github.com/weeravit/task-management/releases/download/v1.0/app-release.apk)

## Features
- [x] Task list by status (todo, doing, done)
- [x] Setup Passcode
- [x] Passcode Lock after 10 seconds of inactivity
- [x] Unit Testing
- [x] Integration Testing
 
## Screenshots
<img width="300" alt="Screenshot 2567-01-05 at 07 57 36" src="https://github.com/weeravit/task-management/assets/14235043/4efb7f00-7c59-46f9-8b75-9828d78d0d3e">
<img width="300" alt="Screenshot 2567-01-05 at 07 57 36" src="https://github.com/weeravit/task-management/assets/14235043/a3a03e6f-226b-4406-a7c7-a489fea9a2da">

## How to Run
- Clone the repository to your local machine using Git:

  ```git clone https://github.com/weeravit/task-management```
- Open the project in Android Studio.
- Wait for Android Studio to sync the project and download any necessary dependencies.
- Connect an Android device or start an emulator.
- Build and run the application by clicking on the "Run" button in Android Studio

```diff
! Note: The app lags when run in debug mode.
```
```diff
+ The solution is to Generate Signed APK using Release Variant or [Download APK](https://github.com/weeravit/task-management/releases/download/v1.0/app-release.apk) to test it.
```

## Unit Testing
The project focuses on Repository and ViewModel.

<img width="311" alt="Screenshot 2567-01-05 at 07 57 36" src="https://github.com/weeravit/task-management/assets/14235043/89050854-f130-4c7a-8798-c9d036ff54e5">

## Integration Testing
The project use UI Automate to execute the E2E happy path.

**To automate, right-click the "MainE2ETest" file and select Run "MainE2ETest"**

<img width="288" alt="Screenshot 2567-01-05 at 08 01 29" src="https://github.com/weeravit/task-management/assets/14235043/97c78dc7-7bde-4f1c-b261-91f8eba35599">
