# Pedals

Pedals is an open source cyclist mapping and route analysis software tool for cyclists across the world. Currently optimised for users in the New York City area.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Make sure Java 8 is installed on your computer. To check your Java version open Terminal (for Linux/Mac users) or Command Prompt (for Windows users) and type in:
```
java.version
```
If java is not recognized or the version is not at least “1.8.XXX” then go to the following website to install Java 8.
https://java.com/en/download/

This is a maven project, so make sure maven is installed on your
computer. Information about installing maven can be found here:
https://maven.apache.org/install.html


### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
git clone https://eng-git.canterbury.ac.nz/jes143/seng202-team10-project.git
or
download: https://eng-git.canterbury.ac.nz/jes143/seng202-team10-project/repository/archive.zip
```


To import the project into your IDE, extract the ZIP, and open an existing project.
Select the 'MainBuild' folder, if you're using IntelliJ the project should load right up.
If your IDE fails to recognize the settings, ensure that your build version is Java Oracle 8, and that your run configuration is on main.Main


Run main.Main to launch the application

## Running the tests

This project uses JUnit for testing. Run the tests using

```
mvn test
```

## Deployment

To build the executable JAR run the command
```
mvn package
```
This will have created a jar file called 
'Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar' and will be located in 
'MainBuild/target'. This file contains all the required Java dependencies. You can safely rename this file to your choosing.

## Built With

* [Google Maps](https://developers.google.com/maps/web/) - Mapping Source
* [GMapsFX](https://github.com/rterp/GMapsFX/) - Mapping Library
* [Maven](https://maven.apache.org/) - Dependency Management

## Documentation

Javadocs can be generated using
```
mvn javadoc:javadoc
```
This will then generate the site in 'MainBuild/target/site/apidocs'.
There are a precompiled set of Javadocs in the main directory under 'Javadoc'. Open the index.html within that folder to view the docs.

## Authors

* **Jack Steel** - *Core developer* - [jackodsteel](http://github.com/jackodsteel)
* **James Toohey** - *Core developer* - []()
* **Braden Alsford** - *Core developer* - []()
* **Matthew King** - *Core developer* - []()
* **Lewis White** - *Core developer* - []()

## License

This project is licensed under the Eclipse Public License 1.0 - see the [LICENSE.md](LICENCE.md) file for details

## Acknowledgments

* University of Canterbury
