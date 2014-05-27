package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Not implements Predicate{

	Predicate pred;
	
	public Not(Predicate pred) {
		this.pred = pred;
	}
	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "NotNode");
		pred.printTree(depth + 1);
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return !pred.evaluate(tuple);
	}

}
