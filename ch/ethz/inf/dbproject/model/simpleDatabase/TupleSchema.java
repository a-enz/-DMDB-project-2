package ch.ethz.inf.dbproject.model.simpleDatabase;

import java.util.Collection;
import java.util.HashMap;

/**
 * The schema contains meta data about a tuple. So far we only store the name of
 * each column. Other meta data, such cardinalities, indexes, etc. could be
 * specified here.
 */
public class TupleSchema {
	private final ColumnInfo[] columnInfos;
	
	/**
	 * Constructs a new tuple schema.
	 * @param columnNames column names
	 */
	public TupleSchema(final String[] columnNames, final String[] columnSize, final String[] columnTables) {
		
		columnInfos = new ColumnInfo[columnNames.length];
		
		for (int i = 0; i < columnNames.length; i++){
			columnInfos[i] = new ColumnInfo(columnTables[i], columnNames[i], Integer.parseInt(columnSize[i]));
		}
	}
	
	public TupleSchema(final String[] columnNames, final int[] columnSize, final String[] columnTables) {
		
		columnInfos = new ColumnInfo[columnNames.length];
		
		for (int i = 0; i < columnNames.length; i++){
			columnInfos[i] = new ColumnInfo(columnTables[i], columnNames[i], columnSize[i]);
		}
	}

	/**
	 * Given the name of a column, returns the index in the respective tuple.
	 * 
	 * @param column column name
	 * @return index of column in tuple
	 */
	public int getIndex(final String column) {
		final Integer index = 0; //TODO:
		if (index == null) {
			return -1; // error
		} else {
			return index;
		}	
	}

	public int getSize(final String column) {
		int index = this.getIndex(column);
		return columnInfos[index].getSize();
	}
	
	public int getSize(final int index){
		return columnInfos[index].getSize();
	}
	
	
	public String getTableName(final int index){
		return columnInfos[index].getTableName();
	}
	
	public int[] getAllSize(){
		int[] result = new int[columnInfos.length];
		for(int i=0; i<columnInfos.length; i++){
			result[i] = columnInfos[i].getSize();
		}
		return result;
	}
	
	public String[] getAllNames(){
		String[] result = new String[columnInfos.length];
		for(int i=0; i<columnInfos.length; i++){
			result[i] = columnInfos[i].getColumnName();
		}
		return result;
	}
	
	public int getOffset(String column){
		int index = getIndex(column);
		int offset = 0;
		for (int i=0; i < index; i++){
			offset += columnInfos[i].getSize();
		}
		return offset;
	}
	
	public int getType(final int index){
		return 0;
		//TODO: return typecode from hashmap
	}
	
	public Integer[] getAllType(){
		return null;
		//TODO: return
	}

}
