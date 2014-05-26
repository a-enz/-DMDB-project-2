package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Greater implements Predicate{
	
	Extractor left;
	Extractor right;
	
	public Greater(Extractor left, Extractor right){
		this.left = left;
		this.right = right;
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		return null;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		int cmp = left.getValue(tuple).compareTo(right.getValue(tuple));
		return (cmp > 0);
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "GreaterNode");
		System.out.println(Helper.indent(depth) + "Left:");
		left.printTree(depth + 1);
		System.out.println(Helper.indent(depth) + "Right:");
		right.printTree(depth + 1);
	}
}