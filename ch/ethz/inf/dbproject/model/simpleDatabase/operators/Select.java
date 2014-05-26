package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.Comparator;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.*;
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.Helper;
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.Predicate;


/**
 * Selection in relational algebra. Only returns those tuple for which the given
 * column matches the value.
 * 
 * This class is a generic to allow comparing any types of values.
 * 
 * i.e. SELECT * FROM USERS WHERE USER_ID=1
 * would require these operators: 
 * 
 * Scan usersScanOperator = new Scan(filename, columnNames);
 * Select<Integer> selectOp = new Select<Integer>(
 * 		usersScanOperator, "user_id", 1);
 * 
 * Similarly, this query:
 * 
 * SELECT * FROM USERS WHERE USENAME=john
 * would require these operators:
 * 
 * Scan usersScanOperator = new Scan(filename, columnNames);
 * Select<String> selectOp = new Select<String>(
 * 		usersScanOperator, "username", "john");
 * 
 */
public class Select extends Operator {

	private final Operator op;
	private String column;
	private Predicate pred;

	
	public Select(final Operator op, final Predicate pred) {
		this.op = op;
		this.pred = pred;
		
	}
	
	@Override
	public boolean moveNext() throws IOException {
		
		// TODO the contents of this method are just to give you an idea of
		// how it should look like. 
		
		// a) retrieve the next tuple from the child operator
		if (!this.op.moveNext()) { 
			return false;
		}
		
		// b) if there is a next tuple, pull it
		Tuple t = this.op.current();
		
		// c) check if this tuple matches our selection predicate
		if (pred.evaluate(t)) {			
			// It does
			this.current = t;
			return true;
			
		} else {
			while(!pred.evaluate(t)){
				System.out.println("Loop: " + pred.evaluate(t) + ", " + t.toString());
				if(this.op.moveNext()){
					t = this.op.current();
				}
				else{
					return false;
				}
			}
			if(pred.evaluate(t)){
				this.current = t;
				return true;
			}
			// TODO: loop until we either find something that matches, 
			// or this.op has no more tuples.
			
			// HINT: try to avoid recursive calls here.
		}
		
		return false;
	}

	@Override
	public String getFileName() {
		return op.getFileName();
	}

	@Override
	public void reset() throws IOException {
		op.reset();
		
	}

	@Override
	public TupleSchema getSchema() {
		return op.getSchema();
	}


	
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getoffset() {
		return op.getoffset();
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "SelectNode");
		System.out.println(Helper.indent(depth) + "Predicate:");
		pred.printTree(depth + 1);
		System.out.println(Helper.indent(depth) + "Operator:");
		op.printTree(depth + 1);
	}

}
