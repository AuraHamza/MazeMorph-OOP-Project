# 🐍 Maze Morph

![Maze Morph](images/Maze_Morph.jpeg)




























Maze Morph is a thrilling JavaFX-based snake game developed as an Object-Oriented Programming (OOP) project by Hamza Salahuddin and Hashir Uddin. The game puts a unique twist on the classic Snake game by adding maze barriers, random obstacles, special power-up foods, and an AI-controlled snake opponent.

> Built entirely in Java using **JavaFX**, this project showcases real-time animations, object-oriented design patterns, and interactive GUI elements.

## 🧠 Game Architecture

### 🔁 Game Flow 
1. **Main**: Sets up JavaFX and launches the application by calling `launch()`, which calls `start(Stage)` and loads the main menu.
2. **MenuScreen**: Displays the game logo, title, and three maze options.
3. **GameWindow**: Initializes the chosen maze screen and creates the game panel.
4. **GamePanel**: Runs the core game logic — including input handling, collision detection, animation loop, power-up mechanics, and AI logic.
---

## 🕹️ Features
- ✅ **Three Maze Types**:
  - Maze Type 1: Border Walls
  - Maze Type 2: Vertical Barriers
  - Maze Type 3: Random Obstacles

- 🧠 **AI Snake Opponent**:
  - Moves randomly around the maze.
  - Gains 1 point for every 2 foods the player consumes.

- 🍏 **Random Food Spawning**:
  - Foods spawn randomly with different types and effects.
  - Each type is rendered in a different color on screen.

- ⚡ **Power-Up Foods**:
  | Type           | Effect                                   | Probability |
  |----------------|------------------------------------------|-------------|
  | Normal         | +1 Point                                 | 70%         |
  | Speed Boost    | Temporarily increases player speed       | 10%         |
  | Shield         | Temporarily allows invincibility         | 10%         |
  | Double Points  | Next food grants double score            | 5%          |
  | Slow Time      | Temporarily slows down AI snake          | 5%          |

 - 🎮 **Controls**:
  - Arrow Keys → Move Player Snake
  - P → Pause Game
  - Space → Restart Game

- 💡 **Game States**:
  - Running
  - Paused
  - Game Over
  - 
- 🔊 **Sound Effects** for eating food, collisions, and transitions.
 
- 🧩 **Modular Design**:
  - Follows core OOP principles: Abstraction, Inheritance, Polymorphism, and Encapsulation.
  - Classes are well-separated (MenuScreen, GameWindow, GamePanel, etc.).
---

## 🛠️ Technologies Used
- **Language**: Java
- **GUI**: JavaFX
- **Tools**: IntelliJ IDEA / Eclipse
- **Concepts**: OOP, AnimationTimer, KeyEvent Handling, Canvas Drawing
