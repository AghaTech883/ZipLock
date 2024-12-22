# Zipper Lock with Biometric Authentication

This Android application features a zipper lock UI integrated with biometric authentication for secure access. The project demonstrates a creative lock screen design paired with fingerprint-based authentication.

## Features

### Zipper Lock Interface:
- Interactive zipper-based lock screen.
- Detects when the zipper is fully open to trigger authentication.

### Biometric Authentication:
- Fingerprint-based authentication for secure access.
- Handles authentication failures and errors gracefully.

### Double Back Press to Exit:
- Requires two consecutive back presses to exit the app.

### Error Handling:
- Notifies users when biometrics are unavailable or authentication fails.

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/AghaTech883/ZipLock.git
   ```
2. Open the project in Android Studio.Build and run the app on a device with fingerprint authentication capabilities.
3. Interact with the zipper lock and authenticate using your fingerprint.
## How It Works
The zipper lock triggers biometric authentication when fully opened.
Successful authentication navigates to the next screen.
Failed authentication displays an error message and resets the lock.
Exiting the app requires two consecutive back presses.
#### License
This project is licensed under the MIT License.

Feel free to fork, contribute, or provide feedback!
