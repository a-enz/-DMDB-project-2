package ch.ethz.inf.dbproject.model.test;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;

public class SQLTest {
	private SQLParser parser;
	private String arg= "Select PersonID, FirstName, SurName, Street, BirthDate, Nationality, Bounty FROM Person WHERE PersonID=12";
	//private String arg="SELECT * FROM Connected WHERE CaseID =11 AND PersonID = 9"
	
	public SQLTest() throws StandardException{
		parser = new SQLParser();
		parser.parseStatement(arg).treePrint();
	}
	
	public static void main(String[] args) throws StandardException {
		new SQLTest();
	}
}


