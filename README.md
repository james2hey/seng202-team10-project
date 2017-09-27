--------------------------------------------
-------------PEDALS README FILE-------------
--------------------------------------------

-HOW TO IMPORT/BUILD PROJECT-

This program runs on the Java 8 Runtime, so it is required that you have a Java 8 installation available.
This program also depends on internet access for many of it's features, although it is not stricty required it is highly reccommended.

The source code is a maven project, so make sure maven is installed on your
computer. Information about installing maven can be found here:
https://maven.apache.org/install.html

To import the project into your IDE, extract the ZIP, and open an existing project.
Select the Source Code/MainBuild folder, if you're using IntelliJ the project should load right up.
If your IDE fails to recognize the settings, ensure that your build version is Java Oracle 8, and that your run configuration is on main.Main


To build the executable JAR, using the command terminal navigate to the directory where the extracted Source Code/MainBuild is located and enter the
Source Code/MainBuild directory. Enter the command:

mvn package

This will have created a jar file called 
'Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar' and will be located in 
'Source Code/MainBuild/target'. This file contains all the required Java dependencies. You can safely rename this file to your choosing.

-HOW TO RUN PEDALS-

Make sure you have successfully built the program using the instructions above.

-Using the terminal-

Using the command terminal, navigate to the directory 'Source Code/MainBuild/target'. Enter
the command:
java -jar Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar

-Using the file brower-

open the file browser and navigate to the directory 'Source Code/ManiBuild/target'. This
directory should contain the jar file 
'Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar'. Right click on the jar file
and select 'open with Oracle Java 8 Runtime'

-JavaDocs-

Javadocs can be generated using mvn javadoc:javadoc, which will then generate the site in 'Source Code/Mainbuild/target/site/apidocs'.
There are a precompiled set of Javadocs in the main directory under 'Javadoc'. Open the index.html within that folder to view the docs.
.
The database is stored in your home directory, under the name 'pedals.db'.
You can move this database between computers to retain your stored information, or delete it to start fresh.

-Unit tests-

Unit tests will be run upon packaging, or can be specifically tested using the command 'mvn test' when in the Source Code/MainBuild.
