# Rule-Engine-with-AST

## Introduction
The Rule Engine with Abstract Syntax Tree (AST) is a 3-tier application designed to determine user eligibility based on various attributes such as age, department, income, and experience. This application allows for the dynamic creation, combination, and modification of rules represented as ASTs, enabling flexible evaluation of user attributes against predefined conditions.

## Technologies Used
- **Backend**: Spring Boot
- **Frontend**: Angular
- **Database**: MySQL
- **Programming Language**: Java (JDK 21)

## Features
- Dynamic rule creation and modification using an Abstract Syntax Tree (AST).
- Rule evaluation based on user attributes.
- API endpoints for creating and evaluating rules.
- User-friendly UI for interacting with the rule engine.
- Error handling for invalid rule strings and data formats.
- Ability to combine multiple rules into a single evaluation.

## Prerequisites
- **Java Version**: JDK 21
- **Node.js** (for Angular development)
- **MySQL** (for the database)
- **Angular CLI** (for managing the Angular application)

## Setup Instructions
1. Clone the git repository using:
   ```bash
   git clone https://github.com/sachinmallabade/Rule-Engine-with-AST.git

2.  Open the RuleEngineApp folder in STS(spring tool suite)
3.  Create a database in MySQL named as rule_engine_db
4.  Run a Project as Spring Boot App
5.  Open the command prompt and redirect to rule-engine-ui folder
6.  make sure that you have installed node js
7.  install angular -- npm install -g @angular
8.  run the command to download all necessary dependencies -- npm install
9.  add angular material -- ng add @angular/material
10.  run angular app --- ng serve
11.  To test the application open browser and search localhost:4200
