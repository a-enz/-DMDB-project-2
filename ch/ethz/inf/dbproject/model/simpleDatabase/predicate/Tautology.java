package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Tautology implements Predicate{

	//if theres no whereclause put in a placeholder for the predicate which returns always true
	
	@Override
	public Visitable accept(Visitor v) throws StandardException {
		return null;
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "Tautology");
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return true;
	}

}
