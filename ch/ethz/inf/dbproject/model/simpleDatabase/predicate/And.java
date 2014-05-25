package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class And extends BinaryOp implements Predicate, Printable {
	
	
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

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "AndNode");
		System.out.println(Helper.indent(depth) + "Left:");
		left.printTree(depth + 1);
		System.out.println(Helper.indent(depth) + "Right:");
		right.printTree(depth + 1);
	}
}
