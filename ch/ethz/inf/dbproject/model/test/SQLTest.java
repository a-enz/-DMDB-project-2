package ch.ethz.inf.dbproject.model.test;

import java.io.IOException;

import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.RelVisitor;
import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;
import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import com.foundationdb.sql.parser.Visitable;

public class SQLTest {
	
	private SQLParser parser;
	private RelVisitor visitor;
	private StatementNode node;
	private Project project;
	private Visitable visit;
	//private String arg= "Select PersonID, FirstName, SurName, Street, BirthDate, Nationality, Bounty FROM Person WHERE PersonID=12";
	//private String arg="SELECT ca.*, Person.name FROM Cases ca, Person WHERE CaseID =11 AND Location = 'Zurich'";
	//private String arg="SELECT ca.CaseNr, ca.Title, ca.Date, ca.Location, ca.Status, ca.DateCon, DateEnd FROM Cases ca, ContainedIn co WHERE ca.CaseNr =  co.CaseID AND CatName = 'Exhibitionism'";

	private String arg="SELECT Title FROM Cases WHERE CaseNr=1 ORDER BY Title ASC";

	public SQLTest() throws StandardException, IOException {
		parser = new SQLParser();
		node = parser.parseStatement(arg);
		node.treePrint(0);
		visitor = new RelVisitor();
		if(node == null) System.out.println("asdf");
		//else System.out.println("fdsa");
		visit = visitor.visit(node);
		System.out.println("Class: " + visit.getClass().toString());
		project = (Project) visit;
		project.printTree(0);
		
		if (project.getSchema() == null){
			System.out.println("AAAAh...");
		}
		
		for (String s:project.getSchema().getAllNames()){
			System.out.println("columnName: " + s);
		}
		System.out.println(project.moveNext());
	}
	
	public static void main(String[] args) throws StandardException, IOException {
		new SQLTest();
	}
}


