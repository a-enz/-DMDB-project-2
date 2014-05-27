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
	private Operator op;
	private Visitable visit;
	//private String arg= "Select PersonID, FirstName, SurName, Street, BirthDate, Nationality, Bounty FROM Person WHERE PersonID=12";
	//private String arg="SELECT ca.*, Person.name FROM Cases ca, Person WHERE CaseID =11 AND Location = 'Zurich'";
	//private String arg="SELECT ca.CaseNr, ca.Title, ca.Date, ca.Location, ca.Status, ca.DateCon, DateEnd FROM Cases ca, ContainedIn co WHERE ca.CaseNr =  co.CaseID AND CatName = 'Exhibitionism'";

<<<<<<< HEAD
	private String arg="SELECT ca.* FROM Cases ca WHERE CaseNr=1 ORDER BY title asc";
=======
	private String arg="SELECT Person.* FROM Person WHERE PersonID = 0";

>>>>>>> 8c60ae43a0de041bf54e924acecaa939d2e9fd39

	public SQLTest() throws StandardException, IOException {
		parser = new SQLParser();
		node = parser.parseStatement(arg);
		node.treePrint(0);
		visitor = new RelVisitor();
		visit = visitor.visit(node);
		System.out.println("Class: " + visit.getClass().toString());
<<<<<<< HEAD
		op = (Operator) visit;
		System.out.println("Next: " + op.moveNext());
		op.printTree(0);
		System.out.println("Tuple: " + op.current());
=======
		project = (Project) visit;
		project.printTree(0);
		System.out.println(project.moveNext());

>>>>>>> 8c60ae43a0de041bf54e924acecaa939d2e9fd39
	}
	
	public static void main(String[] args) throws StandardException, IOException {
		new SQLTest();
	}
}


