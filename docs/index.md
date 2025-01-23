---
layout: default
title: HabiPet
---

# App Concept

**HabiPet** is a habit-building app that allows users to set and track personal habits by defining goals for daily and weekly completion. Consistently completing habits rewards users with in-app coins, which they can spend in the shop to purchase animated pets, unique skins, and customizable backgrounds.

These features aim to motivate users through positive reinforcement, gamification, and interactive rewards - making habit-building an engaging experience.

### Goals & Features

- **Habit creation and tracking** (CRUD functionality)
- **Virtual pet interaction and progression** (XP, level, and customization)
- **Calendar and tracking of habit completion**
- **Shop for customization and rewards** (Pet skins and habitats)

# Database Overview

## Tables

1. **Habits Table**: Stores habit details (name, description, repetition, streak, icon, color, etc.).
   - Habits have a one-to-many relationship with `PetStats` (completion rewards XP).
   - Habits link to `PetStats.coins` since you earn coins by reaching a habit’s streak goal.
   
2. **PetStats Table**: Stores data related to the pet (name, level, XP, skin, habitat, coins, etc.).

### Data Flow

1. **HabiPetDatabase**: Creates the Room database for the entire project (saving `Habit` and `PetStats`).
2. **Habit & PetStats Entity and DAO**: Controls how data (habit- or pet-related) is saved to the Room database.
3. **Habit & PetStats ViewModel**: All data-related operations are executed within the `viewModelScope`. The ViewModel acts as an intermediary between the UI and data layer for both habits and petStats.
4. **Habit & PetStats Repository**: Provides a clean and organized way to manage habit or pet data and its interactions with other parts of the application.
   - Handles the actual CRUD logic for `Habit` (e.g., `addHabit`, `completeHabit`) and `PetStats` (e.g., `updateXP`, `buyShopItem`).

# App Showcase

## Views

- **HomeScreen**: Displays a list of recent habits, a habit calendar, and the `CompleteHabitCard`, where habits can be marked as completed.
  
- **PetScreen**: Shows detailed pet stats, animations, and interactions (e.g., tap animation)
  - Clicking “Customize” navigates to `CustomizePetScreen`, where you can set `PetStats.skin` and `PetStats.habitat`.
   
- **AddHabitScreen**: Allows users to input new habits and choose repetition, icon, and color
  
- **HabitsScreen**: Provides an overview of all habits stored in the database
  
- **HabitDetailsView**: Displays a detailed overview of a selected habit (includes an edit button)
  - **EditHabitView**: Enables users to edit or delete the habit
    
- **ShopScreen**: Features purchasable skin and habitat items for the PetScreen
  - Purchased items are added to `PetStats.ownedSkins` or `PetStats.ownedHabitats` to save them for later use in `CustomizePetScreen`.

## Components

- **TopHeaderBar**: Contains the logo, heading, and a display for `PetStats.coins`
  
- **BottomNavBar**: Contains the main navigation routes (Home, Pet, Add Habit, Habits, and Shop)
  
- **RepetitionSelector & IconAndColorSelector**: Used in `AddHabitScreen` and `EditHabitView` to select a predefined habit repetition, icon, and color.
  - If `habit.repetition` is set to "Test", the cooldown for completing a habit is only one minute - that is to efficiently test the habit completion and receive rewards (`petStats.coins`) during a usability test.
    
- **HabitListItem**: Used for displaying habits in lists (HomeScreen or HabitsScreen). Clicking it navigates to the habit’s details page.
  
- **HabitCompleteCard**: Enables users to mark a habit as completed based on its repetition
  
- **ShopItemCard**: Displays a single shop item with a preview of a skin or habitat in the ShopScreen
  
- **PetLevelBar**: Linear progress bar used in the PetScreen to display the pet’s current XP and level

## Util

Utility objects provide calculations and reusable functionalities across the app.

- **HabitUtil**: Contains helper functions for managing habit-related data, such as color, streak goals, rewards, and time progress calculations.
- **ImageUtil**: Maps string identifiers (e.g., habit icons, pet skins, and habitats) to their corresponding drawable resources.
- **GifUtil**: Similar to `ImageUtil`, it provides access to GIF and image resources for animations on the PetScreen.

# Usability Test Plan

## Purpose and Research Questions

The primary goal of the user test is to evaluate the usability, intuitiveness, and enjoyment of our habit-building app. Specifically, we try to answer the following key questions:

- How intuitive is it for users to create, complete, and manage habits in the app?
- Are the shop and pet interaction features engaging and easy to use?
- What areas of the app, if any, confuse or frustrate users?

Our app is designed for a wide audience since habits are universal and relevant for all age groups. However, the app's cute aesthetic may particularly appeal to younger users and potentially skew toward female users. We plan to recruit at least five participants for our user test.

## Data to Collect

To evaluate the app effectively, we will collect the following data:

- **Task Completion Time:** Measure the time it takes for users to complete each task, providing an objective measure of efficiency.
- **Task Completion Success:** Record whether participants can complete tasks without assistance (success or failure).
- **Perceived Ease of Use:** Use the Single Ease Question (SEQ) after each task to understand the perceived difficulty on a scale of 1 (Very Difficult) to 7 (Very Easy).
- **Overall Usability and Satisfaction:** Answer the System Usability Scale (SUS) after all tasks to gather insights into overall user satisfaction.
- **Qualitative Feedback:** Gather some qualitative feedback through open-ended questions to explore user experiences and suggestions.

#### Potential Qualitative Questions

- What did you enjoy most about the app?
- Was anything in the app frustrating or confusing?
- What feature or improvement would you suggest to make the app better?

### Methods to Use

Participants will complete a series of tasks designed to evaluate core app functionality:

1. Create a Habit
2. Complete a Habit (You don’t have to actually do the task in real life)
3. Buy something from the shop
4. Interact with one of your pets

Each task will be clearly outlined in a task list provided to participants. As previously stated, we will use the SEQ after each task individually and also the SUS after all tasks are completed.

#### Recording and Observation

We will use screen-recording software to capture user interactions, focusing on navigation paths, errors, and behavior.

#### Post-Test Interview

After completing the tasks, we will conduct a brief interview to ask qualitative questions and clarify observations made during testing. This will provide additional insights into the user experience and areas for improvement.

## Data Visualization

To present the collected data effectively, we will use the following methods:

- **Task Completion Time:** Represented as a bar chart showing the average time taken for each task across all users.
- **Task Completion Success:** Displayed using a stacked bar chart to illustrate the number of users who successfully completed each task versus those who did not.
- **SEQ Results:** Shown as either a line chart or a bar chart to visualize the average SEQ score for each task.
- **SUS Score:** Depicted using a box plot to show the distribution of SUS scores among participants or as a single bar representing the average SUS score.
- **Qualitative Feedback:** Summarized in a word cloud to highlight recurring themes or presented in a table to categorize user suggestions and issues.

## Heuristic Evaluation

We analyzed the current version (20.01.2025) of our Mobile Application to find potential problems which we can work on in the coming week as well as analyze from the feedback we will gather in our upcoming user test.

### 10 Usability Heuristics

1. Visibility of System Status
2. Match Between System and the Real World
3. User Control and Freedom
4. Consistency and Standards
5. Error Prevention
6. Recognition Rather Than Recall
7. Flexibility and Efficiency of Use
8. Aesthetic and Minimalist Design
9. Help Users Recognize, Diagnose, and Recover from Errors
10. Help and Documentation

### Issues Table

| Issue | Description | Heuristic | Severity |
|-------|-------------|-----------|----------|
| 1 | The shop does not display all categories of items on first sight, you have to scroll down to see that there are different buying options than just pets. | 6. Recognition Rather Than Recall | 3 |
| 2 | In the Shop right now, you have to scroll all the way down to the section "Habitats" without a faster way to see only habitats, like for example filtering for only Backgrounds. | 7. Flexibility and Efficiency of Use | 5 |
| 3 | In the "Customize" window when you have a lot of different skins it would maybe be easier to filter for something you want to add instead of scrolling through. | 7. Flexibility and Efficiency of Use | 4 |
| 4 | On the calendar page right now, there is not really a way to switch to different months than the one you are currently in. Even though you maybe want to view your old achievements. | 3. User Control and Freedom | 7 |
| 5 | In the current version the "Icon" input field in the "Add New Habit" page seems a bit confusing because a user would not know what to input here by default. | 9. Help Users Recognize, Diagnose, and Recover from Errors | 7 |
| 6 | There is no description about what the app is used for which could potentially confuse people who download the app out of curiosity without having ever used a habit app. | 10. Help and Documentation | 5 |

# User Test Results

## Further App Improvements?
- Improve Utility files to calculate habit time progress more efficiently - Currently, a habit can only be completed after a specific time (e.g.: You have to wait 24 hours to complete a "Daily" habit again).
  - We saved all pet animation GIFs to the /res/drawable, since the room database was already set up when we finished the animations. Saving & preloading so many GIFs from resources makes the .apk unnecessarily large and seems to compromise performance, especially when switching pet skins. Additionally, the animation loading and logic in general could be improved.
    
- Originally, we planned to implement **push-notifications** for completing habits, but there simply wasn't enough time.

# Summary & Reflection

## Personal Challenges

## Conclusion
