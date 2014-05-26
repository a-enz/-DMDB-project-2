package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.ParseException;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class ColumnRef implements Extractor, Printable{
	private String column;
	private String table;
	
	public ColumnRef(String column, String table){
		this.column = column;
		this.table = table;
	}
	
	public Value getValue(Tuple tuple) {
		try {
			return new Value(tuple.get(column, table), tuple.getType(column, table));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		return null;
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "ColumnRefNode: " + table + "/" + column);
	}
	
	
}