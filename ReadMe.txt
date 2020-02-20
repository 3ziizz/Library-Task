Take Home test
--------------

1. Requirements Files:

	a. OnicaTask.java
	b. test.sql
	
2. SQL file format:
	1.Assumed the dataBase format will be like that:
		ID:<id(int)>, Title: <title(String)>, Author: <author(String)>, Description: <description(String)>
	2. each line assumed like a new record in dataBase.	


3. To Compile File:
	In Terminal Enter that Command:	javac OnicaTask.java

4. To Run File:
	In Terminal Enter that Command: java OnicaTask

5. That Screen Will Appear:
	==== Book Manager ====
		1) View all books
		2) Add a book
		3) Edit a book
		4) Search for a book
		5) Save and exit
	Choose [1-5]:

Notes : you must enter numbers only in that case (Main Page) , if you enter any character that line will print "Sorry its an invalid input :(" and it will be closed automatically.

6. Steps:
	a. Enter 1 (If you want  to View all books):
		will show all the books that application read it from dataBase with its id and title.

	b. Enter 2 (If you want  to Add a books):
		will generate id (greater than graetest id in db) then allow you to enter book details : Title, Author and Description
		after Enter three above , You should press <Enter> to back to Main Page.
		note: if you want to genrate unique id according to (year+month+min+sec) comment line 172 and uncomment line 171

	c. Enter 3 (If you want  to Edit a books):
		will show all the books that application read it from dataBase with its id and title,you should enter book's id that wants to edit.
		Note : for the following input if you do not enter value and press enter it save old value otherwise the new one.

	d. Enter 4 (If you want  to Search for a books):
		here you should enter an exist title in DB 
		if you entered a correct title or title contain the input it will appear and you should choose between each of them to get more details.
		if you entered a wrong title , then you should press enter to return to main page.

	e. Enter 5 (If you want  to save and exit):
		to write in Db and close the application.
