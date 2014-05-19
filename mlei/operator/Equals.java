package mlei.operator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Equals implements Predicate{
	
	Value left;
	Value right;
	
	public Equals(){
		
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		// TODO Auto-generated method stub
		return left.getValue(tuple) == right.getValue(tuple);
	}
}
