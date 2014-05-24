package ch.ethz.inf.dbproject.myDatabase;

import java.io.IOException;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

public class ResultSet {
	
	final private Operator op;
	
	public ResultSet(Operator op) {
		this.op = op;
	}
	
	public boolean next() throws IOException{
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
	
	//TODO implement all of the below methods
	public String getDate(final String column){ //return String or Date type?
		return null;
	}
	
	public void close(){
		
	}
	
	public boolean last(){
		return false;
	}
}
