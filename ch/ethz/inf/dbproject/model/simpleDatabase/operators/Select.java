package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.Comparator;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.*;
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
public class Select<T> extends Operator {

	private final Operator op;
	private String column;
	private T compareValue;
	private Predicate pred;

	/**
	 * Contructs a new selection operator.
	 * @param op operator to pull from
	 * @param column column name that gets compared
	 * @param compareValue value that must be matched
	 */
	public Select(final Operator op, final String column, final T compareValue) {
		this.op = op;
		this.column = column;
		this.compareValue = compareValue;
	}
	
	public Select(final Operator op, final Predicate pred) {
		this.op = op;
		this.pred = pred;
		
	}

	private final boolean accept(final Tuple tuple) {
		final int columnIndex = tuple.getSchema().getIndex(this.column);
		
		if (tuple.get(columnIndex).equals(this.compareValue.toString())) {
			return true;
		} else {
			return false;
		}
		
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
		if (this.accept(t)) {			
			// It does
			this.current = t;
			return true;
			
		} else {
			while(!this.accept(t)){
				if(this.op.moveNext()){
					t = this.op.current();
				}
				else{
					return false;
				}
			}
			if(this.accept(t)){
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

}
