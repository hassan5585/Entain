# Project Structure

## Build

The build is set up using convention plugins that take care of common build logic across modules.

## Multi-module

The thought is to have the app module depend on different feature modules each of them divided into

- data
- domain
- navigation
- ui

The dependency structure between them looks like this
data -> domain
navigation -> domain
ui -> domain and navigation
domain -> should not depend on any feature module but can depend on core modules

## Concurrency

Concurrency is achieved by standard kotlin coroutines and flow apis

## UI

UI is constructed through jetpack compose using material3 components. It is all wrapped inside a
custom Entain theme.

## Navigation

Navigation is done through compose-navigation library. Each `ui` module is supposed to provide their
navGraph into a set through dagger. The set is then looped through and loaded in the `app` module.
For an example, please see `RaceUiActivityModule`

## Code structure

The project follows general clean architecture guidelines with viewmodels exposing state to the view
and interacting with the data layer through
either usecases or repositories. In this case, only a usecase was needed.

## Networking

Api requests over http are done through `Retrofit`

## Testing

Tests for the two most important classes in this project have been added(where majority of the logic
lies)

- ObserveNextRacesUsecaseTest
- RaceViewmodelTest
  To run the tests, please run `./gradlew test` on the terminal

# Linting

For linting, spotless(ktlint) has been integrated with a few custom rules

# Documentation

Public classes and interfaces have documentation on how to use them. The implementation classes for these
interfaces are internal.

# Given more time, what could be done better

- UI could have been beautified and error/loading states could be more nuanced
- Tests for the mapping layer can be added
- Convention plugin mixes ui and navigation dependencies, those could be broken up
- UI tests could be added
