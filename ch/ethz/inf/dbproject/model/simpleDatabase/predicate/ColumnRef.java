package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.ParseException;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class ColumnRef implements Extractor{
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
			return new Value(column, type);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}
}