--------------------------------------------
-------------PEDALS README FILE-------------
--------------------------------------------

-HOW TO IMPORT/BUILD PROJECT-

The source code is a maven project, so make sure maven is installed on your
computer. Information about installing maven can be found here:
https://maven.apache.org/install.html

Extract the MainBuild directory from the zip. Using the command terminal,
navigate to the directory where the extracted MainBuild is located and enter the
MainBuild directory. Enter the command:

mvn package

This will have created a jar file called 
'Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar' and will be located in 
'MainBuild/target'


-HOW TO RUN PEDALS-

Make sure you have successfully built the program using the instructions above.


-Using the terminal-

Using the commond terminal, navigate to the directory 'MainBuild/target'. Enter
the command:
java -jar Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar


-Using the file brower-

open the file browser and navigate to the directory 'ManiBuild/target'. This
directory should contain the jar file 
'Pedals-1.0-SNAPSHOT-jar-with-dependencies.jar'. Right click on the jar file
and select 'open with Oracle Java 8 Runtime'