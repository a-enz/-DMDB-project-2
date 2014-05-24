package ch.ethz.inf.dbproject.model.simpleDatabase;

import java.util.Collection;
import java.util.HashMap;

/**
 * The schema contains meta data about a tuple. So far we only store the name of
 * each column. Other meta data, such cardinalities, indexes, etc. could be
 * specified here.
 */
public class TupleSchema {

	private final String[] columnNames;

	private Integer[] columnSize;

	private final HashMap<String, Integer> columnNamesMap;
	
	/**
	 * Constructs a new tuple schema.
	 * @param columnNames column names
	 */
	public TupleSchema(final String[] columnNames, final String[] columnSize) {

		this.columnSize = new Integer[columnSize.length];
		
		int index = 0;
		for(String size:columnSize){

			this.columnSize[index] = Integer.parseInt(size);
			index++;
		}
		
		this.columnNames = columnNames;
		
		this.columnNamesMap = new HashMap<String, Integer>();
		for (int i = 0; i < columnNames.length; ++i) {
			this.columnNamesMap.put(this.columnNames[i].toUpperCase(), i);
		}
	}
	
	public TupleSchema(final String[] columnNames, final Integer[] columnSize) {
		this.columnSize = columnSize;
		
		this.columnNames = columnNames;
		
		this.columnNamesMap = new HashMap<String, Integer>();
		for (int i = 0; i < columnNames.length; ++i) {
			this.columnNamesMap.put(this.columnNames[i].toUpperCase(), i);
		}
	}

	/**
	 * Given the name of a column, returns the index in the respective tuple.
	 * 
	 * @param column column name
	 * @return index of column in tuple
	 */
	public int getIndex(final String column) {
		final Integer index = this.columnNamesMap.get(column.toUpperCase());
		if (index == null) {
			return -1; // error
		} else {
			return index;
		}	
	}

	public int getSize(final String column) {
		int index = this.getIndex(column);
		return columnSize[index];
	}
	
	public int getSize(final int index){
		return columnSize[index];
	}
	
	public int getType(final int index){
		return 0;
		//TODO: return typecode from hashmap
	}
	
	public String getTableName(final int index){
		return null;
		//TODO: Return Table Name
	}
	
	public Integer[] getAllSize(){
		return columnSize;
	}
	
	public String[] getAllNames(){
		return columnNames;
	}
	
	public int getOffset(String column){
		int index = getIndex(column);
		int offset = 0;
		for (int i=0; i < index; i++){
			offset += columnSize[i];
		}
		return offset;
	}
	
	public Integer[] getAllType(){
		return null;
		//TODO: return
	}

}
