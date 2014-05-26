package ch.ethz.inf.dbproject.model.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
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
		Operator op = new Scan("cases");
		String expected = "1,Daniel,bli,1 2,Mathias,blo,2 3,Andi,ble,3";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

//	@Test
//	public void testSelect() throws IOException {
//		System.out.println("----------testSelect--------");
//		Operator op = new Select<String>(new Scan("cases.txt"), "name", "Daniel");
//		String expected = "1,Daniel,bli,1";
//		String actual = concatTuples(op);
//		System.out.println("=" + expected + "=");
//		System.out.println("=" + actual + "=");
//		assertEquals(expected, actual);
//	}

	@Test
	public void testProjectByName() throws IOException {
		System.out.println("----------testProjectByName--------");
		Operator op = new Project(new Scan("cases"), "CaseNr", "cases");
		String expected = "Daniel Mathias Andi";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

	@Test
	public void testProjectByIdStatus() throws IOException {
		System.out.println("----------testProjectByIdStatus--------");
		String[] columns = new String[] { "CaseNr", "Location" };
		String[] tables = new String[] { "cases", "cases"};
		Operator op = new Project(new Scan("cases"), columns, tables);
		String expected = "Daniel,1 Mathias,2 Andi,3";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected, actual);
	}

//	@Test
//	public void testSelectProject() throws IOException {
//		System.out.println("----------testSelectProject--------");
//		Operator op = new Project(new Select<String>(new Scan("cases.txt"), "name", "Daniel"), "name", "cases");
//		String expected = "Daniel";
//		String actual = concatTuples(op);
//		System.out.println("=" + expected + "=");
//		System.out.println("=" + actual + "=");
//		assertEquals(expected, actual);
//	}
	
	@Test
	public void testCross() throws IOException{
		System.out.println("----------testCross--------");
		Operator op = new Cross(new Scan("cases"), new Scan("cases"));
		String expected= 	"1,Testverbrechen,2014-04-01,St.Gallen,open,2014-04-12,2014-05-13,1,Testverbrechen,2014-04-01,St.Gallen,open,2014-04-12,2014-05-13 " +
							"1,Testverbrechen,2014-04-01,St.Gallen,open,2014-04-12,2014-05-13,2,Schwerverbrechen,2014-04-26,Bern,open,UNKNOWN,UNKNOWN " +
							"2,Schwerverbrechen,2014-04-26,Bern,open,UNKNOWN,UNKNOWN,1,Testverbrechen,2014-04-01,St.Gallen,open,2014-04-12,2014-05-13 " +
							"2,Schwerverbrechen,2014-04-26,Bern,open,UNKNOWN,UNKNOWN,2,Schwerverbrechen,2014-04-26,Bern,open,UNKNOWN,UNKNOWN";
		String actual = concatTuples(op);
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testSqlParser() throws StandardException, IOException{
		System.out.println("------------testSQLStatement-----------");
		MyDatabase mdb = new MyDatabase();
		Statement stmt = mdb.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Person.* FROM Person WHERE PersonID = 0");
		System.out.println(rs.next());
		System.out.println(rs.getString("firstname"));
		System.out.println(rs.getString("surname"));
		System.out.println(rs.next());
	}

	@Test
	public void testSqlParser2() throws StandardException, IOException{
		System.out.println("------------testSQLStatement2-----------");
		MyDatabase mdb = new MyDatabase();
		Statement stmt = mdb.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT per.FirstName FROM Person per, Cases ca WHERE per.PersonID = ca.CaseNr");
		System.out.println(rs.next());
		System.out.println(rs.getString("firstname"));
		System.out.println(rs.next());
		System.out.println(rs.getString("firstname"));
		System.out.println(rs.next());
	}
	
	@Test
	public void testMultiKey(){
		System.out.println("-------------testMultiKey----------------");
		String[] columns = "co1,co2,co1".split(",");
		String[] sizes = "10,10,100".split(",");
		String[] tables = "ta1,ta1,ta2".split(",");
		String[] types = "0,0,0".split(",");
		
		TupleSchema schema = new TupleSchema(columns, sizes, tables,types);
		
		Integer expected = 1;
		Integer actual = schema.getIndex("co2", "bla");
		
		System.out.println("=" + expected + "=");
		System.out.println("=" + actual + "=");
		assertEquals(expected,actual);
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
 