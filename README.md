# Building Management Application

[![Build Status](https://travis-ci.org/krokodilj/kits.svg?branch=nz%2Fuser-reg-tests)](https://travis-ci.org/krokodilj/kits)
[![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.timsedam:building-management)](https://sonarqube.com/api/badges/gate?key=com.timsedam:building-management)

College course "Construction and Software Testing" project assignment - Building Management application.

### Prerequisites

What things you need to install the software and how to install them:
maven - sudo apt-get install maven
java 1.8 - sudo apt-get install default-jdk
mysql server - sudo apt-get install mysql
mysql workbench - sudo apt-get install mysql-workbench

### Installing

A step by step series of examples that tell you have to get a development env running

clone this project to your machine - git clone https://github.com/krokodilj/kits.git
relocate to project's home dir - cd kits
create database schemas - mysql < create schema kts_test

endpoints can be tested with curl, example:
curl -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:8080/api/auth/login

## Running the tests

Tests are ran with maven goal: mvn test

## Deployment

Deployment is ran with maven goal: mvn deploy

## Contributing

All contributions via PR's or Issues are welcomed and encouraged :)


## Authors

* **Mijura** - [Mijura](https://github.com/Mijura)
* **krokodilj** - [krokodilj](https://github.com/krokodilj)
* **C0mpy** - [C0mpy](https://github.com/C0mpy)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
