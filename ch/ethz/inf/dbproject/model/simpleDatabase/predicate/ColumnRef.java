package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.ParseException;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class ColumnRef implements Extractor, Printable{
	private String column;
	private String table;
	private int type;
	
	public ColumnRef(String column, String table, int type){
		this.column = column;
		this.table = table;
		this.type = type;
	}
	
	public Value getValue(Tuple tuple) {
		try {
			return new Value(tuple.get(column, table), type);
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
		System.out.println(Helper.indent(depth) + "ColumnRefNode: " + table + "/" + column + "/" + type);
	}
	
	
}