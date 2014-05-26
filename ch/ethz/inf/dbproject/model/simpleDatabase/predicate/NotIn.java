package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.io.IOException;
import java.util.ArrayList;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class NotIn implements Predicate{
	
	private ArrayList<Tuple> table;
	private Operator op;
	
	public NotIn(Operator op) throws IOException {
		table = new ArrayList<Tuple>();
		this.op = op;
		while(op.moveNext()) {
			table.add(op.current());
		}
	}
	

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return !table.contains(tuple);
	}


	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "NotInNode");
		op.printTree(depth + 1);
	}

}
