Project Overview

    Authors:[
        Anas Abdulrazzaq,
        Allison Mcdonald,
        Raymond Rantala,
        Christina Reiss,
        Ryan Austin]

    Server:
        Spring Boot v2.3.3

    Client:
        Angular v10.0.7

    Build Tool:
        Gradle v6.5.1

    Database:
        TBD

* * * * * * * * * * * * * * * * * * * * * * * * 

Timeline

    Week 1: Group Formation

    Week 2: Planning and Setup
        [] Planning Documentation
        [x] Initial Setup of Client and Server
        [x] Basic login protocol

    Week 3: User's Guide and Test Plan
        [] User's Guide
        [] Test Plan
            [] Write all User Stories
            [] Setup E2E Test Environment
            [x] Setup Unit Test Environment
            [x] Setup Code Coverage (Jacoco)
            [] Create Test Database
        [] Setup Everyone's Workstations
            [] Synced with GitLab
            [] Can compile/Build
            [] Can load Web Server
            [] Can load Client
    
    Week 4: Design
        [] Design State Objects (DAO's) and their Properties
            [x] User/User Profile
            [] Dog(s)/Dog(s) Profile
            [] Park/Park Info
            [] Calendar Object Model (backend)
        [] Design Decisions
            [] Password management
            [] Sign in protocol
            [] Sign up protocol
        [] UI Layout

    Week 5: Development Phase I
        [] Backend
            [] Controllers
                [x] User Controller
                [] Dog Management Controller
            [] Endpoints
                [x] Add User (Sign Up)
                [x] Login
                [] Logout 
                [] Add Dog to User
                [] Remove Dog from User
            [] Services
                [x] Basic User Service
                [] Dog Management Service
        [] Client
            [] Sign Up Page with Form
            [] Initial Calendar Page Design (Stretch Goal)
        [] Database
            [] Setup User Database
            [] Setup Park Calendar Database
    
    Week 6: Development Phase II
        [] Backend 
            [] Controllers
                [] Calendar Controller
            [] Endpoints
                [] Update Profile
                [] Get Calendar Month
                [] Get Calendar Day
            [] Services
                [] Calendar Scheduling Service
            [] Security
                [] Use Secure Method to handle and store passwords
                [] Check for security concerns
        [] Client
            [] Design or finish Calendar Page
            [] Profile (Update) Page
        [] Database
            [] Populate Database with a lot of Database
            [] Document actual User/Passwords for tests/demos
    
    Week 7: Development Phase III
        [] End to End TBD

    Week 8: Wrap Up and Final Documentation

* * * * * * * * * * * * * * * * * * * * * 

User Story Requirements:
- Input(s)
- Step by Step (instructions/user actions)
    - Keep abstract if possible
- Expected Output(s)
    - Assert by Type
    - Assert by Value
    - Assert Exception
    - Assert that a method was called
