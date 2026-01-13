# Endless Runner Car Game – Android (Kotlin)

This project is an Android endless-runner car game developed in Kotlin.
The goal of the project is to practice clean separation between **game logic** and **UI**, while integrating common mobile capabilities such as **sensors / buttons controls**, **sound**, **vibration**, **persistent leaderboard storage**, and **Google Maps**.

The player drives along a multi-lane road, avoids obstacles, and collects **collectables** (coins). The score increases over time and via collectables, and the best runs are stored and displayed in a Top-10 leaderboard.

* * *

## Project Description

The game is structured to keep the core gameplay rules and state management independent from Activities/Views.
System resources (audio, vibration, sensors, location permissions) are handled in dedicated utility components to keep lifecycle handling safe and predictable.

The project includes multiple screens (landing/menu, gameplay, leaderboard, map preview), persistent score storage, and feedback mechanisms such as sound and vibration.

* * *

## Main Features

- Endless runner gameplay on a multi-lane road
- Player movement left/right across lanes (buttons and/or tilt, depending on your configuration)
- Obstacles spawning and collision handling with **lives**
- **Collectables** spawning and scoring
- Background music + sound effects
- Vibration feedback on hits / events
- Persistent **Top-10** leaderboard (no database required)
- Navigation across multiple Activities and (optionally) Fragments
- Google Maps integration for score location / map preview

* * *

## Project Structure

> Package names below match a typical clean Android game structure. Adjust them to your exact project packages.

### `data`

Responsible for models and persistence:

- `player/PlayerPrefs` – stores player name / settings
- `leaderboard/LeaderboardEntry` – score record model (name, score, timestamp, optional lat/lon)
- `leaderboard/LeaderboardStorage` – saving/loading Top-10 scores (SharedPreferences / JSON)

* * *

### `logic`

Core game rules and state:

- `game/GameManager` – game state, ticks, spawn rules, scoring, collisions, lives, game over
- `game/GameConfig` – lanes/rows/tick speed, difficulty parameters
- `input/ControlMode` – buttons vs tilt (if supported)
- `input/TiltDetector` – accelerometer listener and normalization (if supported)

* * *

### `ui`

User interface layer:

- `landing/LandingActivity` – player name, control mode selection, start game, open leaderboard
- `main/MainActivity` – gameplay screen, rendering grid, HUD updates (score/lives/collectables)
- `leaderboard/LeaderboardActivity` – Top-10 scores list + map / details navigation

* * *

### `utilities`

Cross-cutting helpers:

- `SoundPlayer` / `SoundManagerAudio` – background music and SFX
- `VibrationUtil` – vibration feedback
- `location/LocationProvider` – location retrieval (optional)
- `permissions/PermissionHelper` – runtime permissions (location, etc.)

* * *

## Google Maps API Key Setup

This project uses Google Maps features that require an API key.
For security reasons, the API key should **not** be committed into the repository.

### Configure the API key

1. Open (or create) the file `local.properties` in the root directory of the project.
2. Add the following line:

