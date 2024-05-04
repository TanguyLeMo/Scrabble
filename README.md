# Software Engineering Course Project - Scrabble

This repository contains the source code for the Scrabble project, developed as part of the Software Engineering course at HTWG.

## Project Overview

The Scrabble project is a part of the Software Engineering course at HTWG made by Hannes Bummele and Tanguy Le Morzadec. This project is implemented in Scala and uses the sbt build tool for managing the project. The goal of this project is to create a digital version of the classic board game, Scrabble. The game includes features such as score calculation, board layout, and letter distribution, similar to the original game. The project follows software engineering principles and practices, including unit testing, continuous integration, and code coverage analysis. The project uses GitHub Actions for continuous integration and scoverage for code coverage analysis. The coverage report is uploaded to Coveralls and the status is displayed in the README file.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 11
- sbt

### Installation

1. Clone the repository
```bash
git clone https://github.com/TanguyLeMo/Scrabble.git
````
### Navigate into the cloned repository
```bash
cd Scrabble
```
### 1. Run the Project
```bash
sbt run
```

### Running the tests
```bash
sbt test
```
### Code Coverage
We use scoverage for code coverage. The coverage report can be generated using the following command:
```bash
sbt clean coverage test coverageReport
```
The coverage report can be found in the target/scala-2.13/scoverage-report directory.

### Continuous Integration
We use GitHub Actions for continuous integration. The workflow is defined in the .github/workflows/scala.yml file.

### Code Coverage Status
The code coverage status is displayed in the README file. The badge is generated using the following command:

[![Coverage Status](https://coveralls.io/repos/github/TanguyLeMo/Scrabble/badge.svg?branch=master)](https://coveralls.io/github/TanguyLeMo/Scrabble?branch=master)

### License
This project is licensed under the MIT License - see the [LICENSE](LISCENCE) file for details.
