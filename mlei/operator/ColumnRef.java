package mlei.operator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class ColumnRef {
	private String column;
	private String table;
	private int type;
	
	public ColumnRef(String column, String table, int type){
		this.column = column;
		this.table = table;
		this.type = type;
	}
	
	public Value getValue(Tuple tuple) {
		return new Value(column, type);
	}
}