package ch.ethz.inf.dbproject.model.test;

import static org.junit.Assert.assertEquals;

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

	private String Case = "id,name,status\n1,Stolen car,open\n2,Fiscal fraud,closed\n3,High speed,open";
	private String Person = "firstname,surname\nDaniel,Yu\nAndi,Enz";
	
	
	@Test
	public void testScan() {
		Operator op = new Scan(new StringReader(Case));
		String expected = "1,Stolen car,open 2,Fiscal fraud,closed 3,High speed,open";
		String actual = concatTuples(op);
		System.out.println("----------testScan--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testSelect() {
		Operator op = new Select<String>(new Scan(new StringReader(Case)), "status", "closed");
		String expected = "2,Fiscal fraud,closed";
		String actual = concatTuples(op);
		System.out.println("----------testSelect--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testProjectByName() {
		Operator op = new Project(new Scan(new StringReader(Case)), "name");
		String expected = "Stolen car Fiscal fraud High speed";
		String actual = concatTuples(op);
		System.out.println("----------testProjectByName--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testProjectByIdStatus() {
		String[] columns = new String[] { "status", "id" };
		Operator op = new Project(new Scan(new StringReader(Case)), columns);
		String expected = "open,1 closed,2 open,3";
		String actual = concatTuples(op);
		System.out.println("----------testProjectByIdStatus--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testSelectProject() {
		Operator op = new Project(new Select<String>(new Scan(new StringReader(Case)), "status", "closed"), "name");
		String expected = "Fiscal fraud";
		String actual = concatTuples(op);
		System.out.println("----------testSelectProject--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCross(){
		Operator op = new Cross(new StringReader(Case), new StringReader(Person), Person.length());
		String expected="1,Stolen car,open,Daniel,Yu 1,Stolen car,open,Andi,Enz 2,Fiscal fraud,closed,Daniel,Yu 2,Fiscal fraud,closed,Andi,Enz 3,High speed,open,Daniel,Yu 3,High speed,open,Andi,Enz";
		String actual = concatTuples(op);
		System.out.println("----------testCross--------");
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testGetCaseById(){
		DatastoreInterface dbInterface = new DatastoreInterfaceSimpleDatabase();
		Case ca = dbInterface.getCaseById(2);
		String expected = "Mathias,2,blo,2";
		String actual = ca.toString();
		System.out.println("----------testGetCaseById--------");
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
		stmt.accept(new parseVisitor());
		//stmt.treePrint();
	}

	/**
	 * Concatenates all tuples returned by the operator. The tuples are separated
	 * by a simple space.
	 * @param op operator to read from
	 * @return concatenated tuples
	 */
	private String concatTuples(Operator op) {
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
 