package ch.ethz.inf.dbproject.model.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;
import ch.ethz.inf.dbproject.model.*;
import ch.ethz.inf.dbproject.myDatabase.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.ParameterNode;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import com.foundationdb.sql.parser.Visitor;
import com.foundationdb.sql.parser.parseVisitor;



/**
 * Unit tests for Part 2.
 * @author Martin Hentschel
 */
public class Part2Test {
	@Test

	public void testScan() throws IOException {
		//Operator op = new Scan(new StringReader(Case));
		System.out.println("----------testScan--------");
		Operator op = new Scan("cases.txt");
		String expected = "1,Daniel,bli,1 2,Mathias,blo,2 3,Andi,ble,3";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testSelect() throws IOException {
		System.out.println("----------testSelect--------");
		Operator op = new Select<String>(new Scan("cases.txt"), "name", "Daniel");
		String expected = "1,Daniel,bli,1";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testProjectByName() throws IOException {
		System.out.println("----------testProjectByName--------");
		Operator op = new Project(new Scan("cases.txt"), "name");
		String expected = "Daniel Mathias Andi";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testProjectByIdStatus() throws IOException {
		System.out.println("----------testProjectByIdStatus--------");
		String[] columns = new String[] { "name", "field3" };
		Operator op = new Project(new Scan("cases.txt"), columns);
		String expected = "Daniel,1 Mathias,2 Andi,3";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testSelectProject() throws IOException {
		System.out.println("----------testSelectProject--------");
		Operator op = new Project(new Select<String>(new Scan("cases.txt"), "name", "Daniel"), "name");
		String expected = "Daniel";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCross() throws IOException{
		System.out.println("----------testCross--------");
		Operator op = new Cross(new Scan("cases.txt"), new Scan("cases.txt"));
		String expected= 	"1,Daniel,bli,1,1,Daniel,bli,1 1,Daniel,bli,1,2,Mathias,blo,2 1,Daniel,bli,1,3,Andi,ble,3 " +
							"2,Mathias,blo,2,1,Daniel,bli,1 2,Mathias,blo,2,2,Mathias,blo,2 2,Mathias,blo,2,3,Andi,ble,3 " +
							"3,Andi,ble,3,1,Daniel,bli,1 3,Andi,ble,3,2,Mathias,blo,2 3,Andi,ble,3,3,Andi,ble,3";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testGetCaseById() throws IOException{
		System.out.println("----------testGetCaseById--------");
		DatastoreInterface dbInterface = new DatastoreInterfaceSimpleDatabase();
		Case ca = dbInterface.getCaseById(2);
		String expected = "Mathias,2,blo,2";
		String actual = ca.toString();
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testStatementNextWord(){		
		Statement stmt = new Statement();
		
		String expected = "Hello";
		StringBuilder input = new StringBuilder("Hello World, its me, Daniel");
		String actual = stmt.getNextWordSpace(input);
		System.out.println("----------testStatement--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testStatementSetColumn(){		
		Statement stmt = new Statement();
		System.out.println("----------testStatementSetColumn--------");		
		StringBuilder input = new StringBuilder("This, is, just, an, test FROM");
		stmt.setcolumn(input);
		for (String str:stmt.getColumn()){
			System.out.println(str);
		}
		System.out.println(input);
	}
	
	@Test
	public void testSqlParser() throws StandardException{
		System.out.println("------------testSQLStatement-----------");
		SQLParser parser = new SQLParser();
		StatementNode stmt = parser.parseStatement("SELECT * FROM Cases WHERE PID = 13");
		//stmt.accept(new parseVisitor());
		//stmt.treePrint();
	}

	/**
	 * Concatenates all tuples returned by the operator. The tuples are separated
	 * by a simple space.
	 * @param op operator to read from
	 * @return concatenated tuples
	 * @throws IOException 
	 */
	private String concatTuples(Operator op) throws IOException {
		StringBuilder buf = new StringBuilder();
		while (op.moveNext()) {
			//System.out.println(op.current().toString());
			buf.append(op.current().toString());
			buf.append(" ");
		}
		// delete last space
		buf.deleteCharAt(buf.length() - 1);
		return buf.toString();
	}
}
 