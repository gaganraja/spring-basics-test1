# Spring Developer - Interview Test 001

The purpose of this test application to to evaluate the awarenes of the developers knowledge of the:
* Spring framework, 
* Security,
* Clarity of code
* Database   

# Starting the application
The application is a standard maven based spring boot application. It is already configured to provide a sample LDAP services and embedded h2 database.

## Pre-requisites

* Apache Maven 3.8.7
* JDK 17

## Launch command

* mvn spring-boot:run

# Database
The embedded h2 database has a simple schema as defined in resources/schema.sql file

The database includes three tables 
* PERSON - basic person details  
* PERSON_QUALIFICATIONS - Qualifications associated to a person 
* QUALIFICATION - Collection of available qualifications
  
The tables are related as 
- PERSON 1:N PERSON_QUALIFICATION N:1 QUALIFICATION

Ref: resources/schema.sql and resources/data.sql

# Test Activities

Provide implementations for the following methods
* DataController.getPersonData
* DataController.recordPersonQualification

As part of your implementations consider
* Security
* Reusable components
* Unit testing

#General Info

## Data Reset
The application uses a simple H2 embedded database held in memory. Each time the app is started the database will be reset, adjust the application.properties if you wish to retain any data changes across application starts

## Application Users
The application uses an ldif LDAP implementation with a number of users and groups defined. 
For the test use the following users

* "ben" - The manager level users password: "benspassword"
* "bob" - A standard user readonly access password: "bobspassword"
