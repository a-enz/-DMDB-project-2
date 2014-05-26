package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Equals implements Predicate{
	
	Extractor left;
	Extractor right;
	
	public Equals(Extractor left, Extractor right){
		this.left = left;
		this.right = right;
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		return null;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return left.getValue(tuple).equals(right.getValue(tuple));
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "EqualsNode");
		System.out.println(Helper.indent(depth) + "Left:");
		left.printTree(depth + 1);
		System.out.println(Helper.indent(depth) + "Right:");
		right.printTree(depth + 1);
	}
}