# Google Sheets Workout Report Wrapper [![Build Status](https://travis-ci.org/mathison42/workout-report-wrapper.svg?branch=master)](https://travis-ci.org/mathison42/workout-report-wrapper)

Purpose: Create a Google Sheet wrapper for Workout Report Tool

### Build
	  gradle build

### Run
    gradle -q run

### Immediate To-Do List:
- [x] Create Basic Runner Script
  - [x] Simple OAuth
  - [x] Simple Get
  - [x] Simple Post
  - [x] Clean Up
  - [x] Short Summary on how to use Runner.groovy
- [ ] Create Sheets Wrapper Classes
  - [x] Authorization Class
  - [ ] Spreadsheet Creation Class
		- [x] Create Blank Spreadsheet
			- [x] Spreadsheet Template 1 - Days "Checked In"
		- [x] Create Blank Tab
		- [x] Create Generic Templates
			- [x] Sheet Template 1 - Exercise x Date
			- [x] Sheet Template 2* - Tab 1: Day 1 x Date. Tab 2: Day 2 x Date...
		- [ ] Clean
  - [x] Get Class
		- [x] Single Values
		- [x] Batch Values
  - [x] Post Class
		- [x] Single Values
		- [x] Batch Values
- [ ] First JUnit Test
  - [ ] Create First Basic Class Tests
  - [ ] Set Gradle Compile Test
  - [ ] Set Gradle Run Test
- [ ] Integrate with Travis CI
  - [x] Build
  - [ ] Test

### Future To-Do List:
- [ ] Create Organized Workout Spreadsheet
  - [ ] Create Spreadsheet
  - [ ] Single (Giant) Spreadsheet - Workout X-Axis: {Dumbell {0:{10:25lbs, 10:25lbs, 8:30lbs} | Date Y-Axis
  - [ ] Single Spreadsheet, Multiple Tabs - Tab #1: Chest Day - Tab #2: Leg Day... Contains the X number of predefied workouts
