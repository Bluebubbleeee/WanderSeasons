# WanderSeasons 

WanderSeasons is an Android application built for the **NIT3213 Final Assignment**. The app allows users to authenticate via a login screen and view a list of dynamic entities returned from a RESTful API, with the ability to view detailed information on each item.

---

##  Features

-  **Login Screen** – Styled UI with Hilt-powered logic and Retrofit-based authentication
-  **Dependency Injection** – Using **Hilt** for clean architecture
-  **API Integration** – RESTful calls via Retrofit for login and dashboard
-  **Dynamic Dashboard** – Displays items (e.g., historical places, fashion items) returned by API
-  **Details View** – Full key-value breakdown with proper formatting
-  **Modern UI** – Card views, styled inputs, scrollable content, and typography

---

## Tech Stack

| Tech       | Description                        |
|------------|------------------------------------|
| Kotlin     | Main development language          |
| Hilt       | Dependency injection framework     |
| Retrofit   | API communication                  |
| ViewModel  | MVVM architecture                  |
| LiveData   | UI state observation               |
| Navigation Component | Fragment navigation     |
| Safe Args  | Argument passing between fragments |
| ConstraintLayout + CardView | Modern UI design  |

---

## Setup Instructions

1. Clone the repository (or open the project in Android Studio):
   ```bash
   git clone https://github.com/Bluebubbleeee/WanderSeasons.git
   ```

2. Build the project in **Android Studio Bumblebee or newer**

3. Ensure the following:
   - Minimum SDK: 28
   - Target SDK: 35
   - Internet permission enabled in `AndroidManifest.xml` (Already taken care of)

4. Run the app on an emulator

---

## API Reference

- **Login**:  
  `POST https://nit3213api.onrender.com/{campus}/auth`  
  Requires: `campus`, `username`, `password`

- **Dashboard**:  
  `GET https://nit3213api.onrender.com/dashboard/{keypass}`  
  Returns a JSON object with `entities`

---

## Sample Login

You may use:
```bash
Campus: sydney
Username: User
Password: s12345678
```

---

## Project Structure (Simplified)

```
com.example.wanderseasons/
├── MyApp.kt                      # Hilt Application class
├── ui/                           # Fragments: Login, Dashboard, Details
├── viewmodel/                    # ViewModel layer
├── model/                        # API data models
├── network/                      # Retrofit API interface
├── di/                           # Hilt modules
└── res/layout/                   # XML layouts
   └──navigation/nav_graph.xml    # Fragments navigation
```

---

## Git History (for Assessment)

- ✔️ Clear, atomic commit messages
- ✔️ Package refactor history preserved

---

## Author

- **Student Name**: Tasnim Chowdhury  
- **Student ID**: s8073439  
- **Course**: NIT3213 Mobile Application Development  
- **University**: Victoria University

---

## Final Notes

This app handles dynamic JSON responses gracefully and follows clean code and MVVM architecture using Hilt. All assignment requirements have been fulfilled.
