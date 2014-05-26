package ch.ethz.inf.dbproject.myDatabase;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

import java.io.*;

public class MyDatabase {
	
	public Statement createStatement(){
		return new Statement();
	}
}
