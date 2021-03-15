# March, 29 2016    
# Rajendra
# 

# First time setup (go to Serivices tab at the left hand top bar >> databases >> Java DB)
# Create a database (right click on the Java DB) named DeepTutorNew  set username: deeptutor and password: spring2013
# NOTE: make sure that the username and password used from the program match.

# create student table.
# In the old deeptutor, student id was written as given id. 
# The following ddl script is applicable for new DeepTutor only.
# We may need other tables if pretest and post test are also supported. But for now,
# We just focusing on interactive part only. Student table is the only table
# we need for now.
CREATE TABLE STUDENT (STUDENTID VARCHAR(255) NOT NULL, ASSIGNEDTASKS VARCHAR(255), DTMODE1 VARCHAR(255), DTMODE2 VARCHAR(255), DTMODE3 VARCHAR(255), DTSTATE VARCHAR(255), FINISHEDTASKS VARCHAR(255), HASACCEPTEDTERMSANDCONDITIONS SMALLINT NOT NULL, ISSPECIALSTUDENT SMALLINT NOT NULL, PASSWORD VARCHAR(255), POSTTEST VARCHAR(255), PRETEST VARCHAR(255), PRIMARY KEY (STUDENTID));

# Insert a user into the database. Change username if it already exists
# remmber the password: welcome1 or change it
# The following user will work from the browser only. To work from the small device, you need to use the device id
# as username. Btw, when the device first time connects to the deeptutor server, a user is created if the user with that device
# id does not exists. So, you create a user yourself to work from the browser.

INSERT INTO DEEPTUTOR.STUDENT (STUDENTID, ASSIGNEDTASKS, DTMODE1, DTMODE2, DTMODE3, DTSTATE, FINISHEDTASKS, HASACCEPTEDTERMSANDCONDITIONS, ISSPECIALSTUDENT, PASSWORD, POSTTEST, PRETEST) 
	VALUES ('test101', 'app.bicycleApple,app.carMosquito,app.rocketMeteor', NULL, NULL, NULL, NULL, NULL, 1, 0, 'welcome1', 'A', 'A');


INSERT INTO DEEPTUTOR.STUDENT (STUDENTID, ASSIGNEDTASKS, DTMODE1, DTMODE2, DTMODE3, DTSTATE, FINISHEDTASKS, HASACCEPTEDTERMSANDCONDITIONS, ISSPECIALSTUDENT, PASSWORD, POSTTEST, PRETEST) 
	VALUES ('test102', 'LP02_PR00,LP02_PR01,LP02_PR02', NULL, NULL, NULL, NULL, NULL, 1, 0, 'welcome1', 'A', 'A');
