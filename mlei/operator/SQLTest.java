package mlei.operator;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.unparser.NodeToString;

public class SQLTest {
	
	SQLParser parser;
	NodeToString printer;
	//String[] statements = {"SELECT cas.*, contin.CaseID as CID FROM Cases cas, ContainedIn contin WHERE cas.CaseNr = contin. CaseID AND contin.CatName = 'Category' AND contin.CasID = 13 GROUP BY CatName LIMIT 0,100"};
	String[] statements = {"SELECT * FROM Cases WHERE Name NOT IN (SELECT Name FROM Person)"};
	public SQLTest() {
		parser = new SQLParser();
		printer = new NodeToString();
		try {
			parser.parseStatement(statements[0]).printSubNodes(0);
		} catch (StandardException e) {
			System.out.println("UNABLE TO PARSE!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SQLTest();
	}
}

