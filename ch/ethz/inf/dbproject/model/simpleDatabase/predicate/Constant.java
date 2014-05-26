package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.ParseException;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class Constant implements Extractor, Printable{

	private Value value;
	
	public Constant(String val, int type){
		try {
			value = new Value(val, type);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Value getValue(Tuple tuple) {
		return value;
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "ConstantNode");
		value.printTree(depth + 1);
	}

}
