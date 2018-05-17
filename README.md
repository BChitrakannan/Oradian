This document contains the setup information
Author: Chitrakannan
Email: bchitrakannan@gmail.com
============================================

Framework used: QAF

Setup Procedure:
-----------------

1. Download the code base from Git
2. Import to any IDE -- Eclipse suits the best :) 
3. Build the project. I have used Ivy.
4. To increase readability, you can download, QAF BDD Editor so that the feature files are displayed with keyword specific 
colors
5. Once the code base is built and all the dependencies are resolved, navigate to "./config/master.xml" and right click and 
select run as TestNG. 
6. Once the execution is completed, right click on "./dashboard.htm" and open with web browser. Recommended browser: 
Mozilla.


Framework Explanation:
----------------------

1. src: 	All the java files are stored in src folder.
2. Config: 	This is testng.xml file. This is the starting point of the script. You can include/exclude the tests you 
			want to run here.
3. Scenario:    All the Behavior Driven Scenarios are written here. Each scenario step has corresponding java definition in 
				the "./src/com.ondot.StepsLibrary.class"
				If you have installed, QAF BDD Editor, you can just 'Ctrl+click' on the step and it will take you to the 
				step definition.
4. Server:      Here the driver executables for the browser is stored. 
5. Resource:    All the resources used by the project is placed here. Ex: locator file, XML Input file and application.properties.
6. Dashboard:   All the files associated with Reporting is stored here. 
7. Test-outputs/Test-Results:  These are default TestNG file.

