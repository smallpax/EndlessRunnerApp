# EndlessRunnerGame – Android (Kotlin)

An Android endless-runner car game written in **Kotlin**, built with a clean separation between **data models**, **game logic**, **input controllers**, and **UI**.  
The player moves between lanes to avoid obstacles and collect **collectables**. Scores are saved locally and displayed in a **Top-10 leaderboard**. A **Google Maps** screen is included for location-based score viewing.

---

## Main Features

- Multi-lane endless runner gameplay
- Lane switching via:
  - On-screen **buttons**
  - **Tilt** controls (accelerometer) via `TiltDetector`
- Obstacles + collision handling with lives / game over flow
- **Collectables** system (coin-style pickups)
- Persistent **Top-10** leaderboard (local storage; no database)
- Google Maps integration (map fragment)

---

## Project Structure

Package root:
`com.example.endlessrunnergame`

### `data`
Data models and persistence helpers.

- `data/game`
  - `CellType` – represents cell content in the game grid
  - `GameConfig` – configuration (lanes, rows, speed, lives, etc.)
  - `GameEvents` – game events (collisions, collectables, game over, etc.)
  - `GameSnapshot` – immutable view of the current game state (for UI rendering)
- `data/leaderboard`
  - `LeaderboardEntry` – a single leaderboard record
  - `LeaderboardStorage` – save/load top scores
- `data/player`
  - `PlayerPrefs` – player settings/preferences (e.g., name, mode)

---

### `interfaces`
Interfaces that decouple input from gameplay.

- `Controller` – unified input API for the game (buttons/tilt)
- `TiltCallback` – callback used by `TiltDetector` to report tilt values

---

### `logic`
Pure gameplay rules and state management.

- `logic/game`
  - `GameManager` – core engine:
    - updates ticks
    - spawns obstacles / collectables
    - handles collisions and scoring
    - produces `GameSnapshot` for the UI
- `logic/input`
  - `ButtonsController` – button-driven controller implementation
  - `ControlMode` – defines which control scheme is active
  - `TiltDetector` – accelerometer-based input detector

---

### `fragments`
Reusable UI components.

- `fragments/MapFragment` – Google Maps fragment screen for viewing locations

---

### `ui`
Activities and UI adapters.

- `ui/landing`
  - `LandingActivity` – entry screen (name, mode selection, navigation)
- `ui/main`
  - `MainActivity` – gameplay screen (renders snapshot, handles HUD)
- `ui/leaderboard`
  - `LeaderboardActivity` – top scores screen
  - `TopTenAdapter` – RecyclerView adapter for leaderboard rows

---

### `utilities`
Cross-cutting utilities.

- `utilities/location`
  - (location-related helpers live here)
- `SoundPlayer` – plays sound effects / music (depending on your implementation)
- `VibrationUtil` – vibration feedback helper

---

## Layout Resources

Located under `app/src/main/res/layout`:

- `landing_page_activity.xml`
- `activity_main.xml`
- `activity_leaderboard.xml`
- `leaderboard_row.xml`
- `fragment_maps.xml`

---

## Google Maps API Key Setup

This project includes a Google Maps fragment (`MapFragment`) and requires an API key.

Recommended approach (do not commit keys):

1. Add your key to `local.properties` (project root):
