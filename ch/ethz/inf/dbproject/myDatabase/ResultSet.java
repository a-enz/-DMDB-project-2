package ch.ethz.inf.dbproject.myDatabase;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

public class ResultSet {
	
	final private Operator op;
	
	public ResultSet(Operator op) {
		this.op = op;
	}
	
	public boolean next(){
		return op.moveNext();
	}
	
	public int getInt(final String column){
		int index = op.current().getSchema().getIndex(column);
		return op.current().getInt(index);
	}
	
	public String getString(final String column){
		int index = op.current().getSchema().getIndex(column);
		return op.current().get(index);
	}
	
	//TODO: we might need more data type, such as date, float,...
}
