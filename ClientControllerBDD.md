# Client Controller BDD Documentation

This document outlines the Behavior-Driven Development (BDD) scenarios for the Client Controller in the Order Management System.

## Overview
The Client Controller handles CRUD operations for client management, including creation, retrieval, update, and deletion of clients identified by CPF.

## Scenarios

### CREATE CLIENT

#### Scenario: Successfully create a new client
**Given** a valid client request with CPF, name, email, and optional phone  
**When** the create client endpoint is called  
**Then** return HTTP 200 OK with the created client response  

#### Scenario: Fail to create client with invalid data
**Given** a client request with invalid CPF  
**When** the create client endpoint is called  
**Then** throw ValidationException  

#### Scenario: Create client with optional phone field
**Given** a client request without phone number  
**When** the create client endpoint is called  
**Then** return HTTP 200 OK with the created client response  

### RETRIEVE CLIENT

#### Scenario: Successfully retrieve client by CPF
**Given** an existing client CPF  
**When** the find by CPF endpoint is called  
**Then** return HTTP 200 OK with the client response  

#### Scenario: Fail to retrieve non-existing client by CPF
**Given** a non-existing client CPF  
**When** the find by CPF endpoint is called  
**Then** throw RuntimeException  

#### Scenario: Successfully retrieve all clients
**Given** clients exist in the system  
**When** the list all clients endpoint is called  
**Then** return HTTP 200 OK with the list of client responses  

#### Scenario: Retrieve empty client list
**Given** no clients exist in the system  
**When** the list all clients endpoint is called  
**Then** return HTTP 200 OK with an empty list  

### UPDATE CLIENT

#### Scenario: Successfully update existing client
**Given** a valid update request for an existing client CPF  
**When** the update client endpoint is called  
**Then** return HTTP 200 OK with the updated client response  

#### Scenario: Fail to update non-existing client
**Given** an update request for a non-existing client CPF  
**When** the update client endpoint is called  
**Then** throw RuntimeException  

### DELETE CLIENT

#### Scenario: Successfully delete existing client
**Given** an existing client CPF  
**When** the delete client endpoint is called  
**Then** return HTTP 204 No Content  

#### Scenario: Fail to delete non-existing client
**Given** a non-existing client CPF  
**When** the delete client endpoint is called  
**Then** throw RuntimeException  

## Test Implementation
The tests are implemented using JUnit 5 with Mockito Extension, focusing on unit testing the controller layer with mocked dependencies. Each scenario follows the Given-When-Then structure with appropriate assertions and verifications.</content>