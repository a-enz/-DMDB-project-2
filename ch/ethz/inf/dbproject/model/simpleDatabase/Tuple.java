package ch.ethz.inf.dbproject.model.simpleDatabase;

import java.sql.Date;

/**
 * A tuple in our database. A tuple consists of a schema (describing the names
 * of the columns) and values. A tuple is created and modified by operators.
 * 
 * You can use String to store the values. In case you need a specific type,
 * you can use the additional getType methods.
 */
public class Tuple {

	private final TupleSchema schema;
	private final String[] values;
	

	public Tuple(final TupleSchema schema, final String[] values) {
		this.schema = schema;
		this.values = values;
	}

	public final TupleSchema getSchema() {
		return this.schema;
	}

	public final String get(final int index) {
		return this.values[index];
	}
	
	public final String get(final String column, final String table){
		int index = this.getSchema().getIndex(column, table);
		//System.out.println(column + " " + table + " " + index + " " + values[index] + " " + schema.getType(index));
		return this.values[index];
	}
	
	public final int getType(final String column, final String table){
		return this.getSchema().getType(column, table);
	}

	public final short getShort(final int index) {
		return Short.parseShort(this.values[index]);
	}
	
	public final int getInt(final int index) {
		return Integer.parseInt(this.values[index]);
	}
	
	public final float getFloat(final int index) {
		return Float.parseFloat(this.values[index]);
	}
	
	public final double getDouble(final int index) {
		return Double.parseDouble(this.values[index]);
	}
	
	// TODO 
	// You may add other custom type getters here
	// i.e. Date, Time
	
	public final String[] getValue(){
		return values;
	}
	
	public final String toString() {
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			buf.append(values[i]);
			if (i < values.length - 1) {
				buf.append(",");
			}
		}
		return buf.toString();
	}
	
	public boolean equals(Object other){
		Tuple otherTuple = (Tuple) other;
		int length1 = otherTuple.values.length;
		int length2 = this.values.length;
		if (length1 != length2){
			return false;
		}
		for (int i = 0; i < length1 && i < length2; i++){
			if (!otherTuple.values[i].equals(this.values[i])){
				return false;
			}
		}
		if (!otherTuple.schema.equals(this.schema)){
			return false;
		}
		return true;
	}

}
