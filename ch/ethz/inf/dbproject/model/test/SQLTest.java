package ch.ethz.inf.dbproject.model.test;

import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.RelVisitor;
import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;
import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

public class SQLTest {
	
	private SQLParser parser;
	private RelVisitor visitor;
	private StatementNode node;
	private Select select;
	//private String arg= "Select PersonID, FirstName, SurName, Street, BirthDate, Nationality, Bounty FROM Person WHERE PersonID=12";
	private String arg="SELECT * FROM Connected co WHERE CaseID =11 AND PersonID = 9";
	//private String arg="SELECT ca.CaseNr, ca.Title, ca.Date, ca.Location, ca.Status, ca.DateCon, DateEnd FROM Cases ca, ContainedIn co WHERE ca.CaseNr =  co.CaseID AND CatName = 'Exhibitionism'";
	
	public SQLTest() throws StandardException {
		parser = new SQLParser();
		node = parser.parseStatement(arg);
		visitor = new RelVisitor();
		if(node == null) System.out.println("asdf");
		//else System.out.println("fdsa");
		select = (Select) visitor.visit(node);
		select.printTree(0);
	}
	
	public static void main(String[] args) throws StandardException {
		new SQLTest();
	}
}


