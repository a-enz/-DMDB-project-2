package mlei.operator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class And extends BinaryOp implements Predicate {
	
	
	public And(Predicate left, Predicate right){
		super.left = left;
		super.right = right;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return left.evaluate(tuple) && right.evaluate(tuple);
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}
}
