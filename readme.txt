How to use the app:
...

Currently, right now.
There are 2 statements to run in cmd prompt.

// Note if using Mac, you need to change ; to : and the username and password must change
// when configuring your database in DatabaseDemo

1)To compile
javac -d classes -cp lib/*;src src/DatabaseDemo.java

2)To run
java -cp lib/*;classes DatabaseDemo.java

The output should be as follows:
appleheng apple
orangetan orange
pearlee pear
duriantoh durian

This allow access to the database after you configure your database.