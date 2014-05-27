package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class In implements Predicate{
	
	private ArrayList<Value> table;
	private Operator op;
	private Extractor ext;
	
	public In(Operator op, Extractor ext) throws IOException, ParseException {
		table = new ArrayList<Value>();
		this.op = op;
		this.ext = ext;
		while(op.moveNext()) {		//cache it
			table.add(new Value(op.current().get(0), op.current().getSchema().getType(0)));
		}
	}
	

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean evaluate(Tuple tuple) {
		return table.contains(ext.getValue(tuple));
	}


	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "InNode");
		op.printTree(depth + 1);
	}

}
