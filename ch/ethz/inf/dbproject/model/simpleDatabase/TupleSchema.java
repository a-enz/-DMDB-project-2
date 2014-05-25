package ch.ethz.inf.dbproject.model.simpleDatabase;

import java.util.Collection;
import java.util.HashMap;
import ch.ethz.inf.dbproject.model.simpleDatabase.*;

/**
 * The schema contains meta data about a tuple. So far we only store the name of
 * each column. Other meta data, such cardinalities, indexes, etc. could be
 * specified here.
 */
public class TupleSchema {
	private final ColumnInfo[] columnInfos;
	private final HashMap<MultiKey,Integer> map;
	
	/**
	 * Constructs a new tuple schema.
	 * @param columnNames column names
	 */
	public TupleSchema(final String[] columnNames, final String[] columnSize, final String[] columnTables) {
		
		columnInfos = new ColumnInfo[columnNames.length];
		map = new HashMap<MultiKey,Integer>();
		
		for (int i = 0; i < columnNames.length; i++){
			columnInfos[i] = new ColumnInfo(columnTables[i], columnNames[i], Integer.parseInt(columnSize[i]));
			MultiKey key = new MultiKey();
			key.setKey(columnTables[i]);
			key.setKey(columnNames[i]);
			map.put(key, i);
		}
	}
	
	public TupleSchema(final String[] columnNames, final Integer[] columnSize, final String[] columnTables) {
		
		columnInfos = new ColumnInfo[columnNames.length];
		map = new HashMap<MultiKey,Integer>();
		
		for (int i = 0; i < columnNames.length; i++){
			columnInfos[i] = new ColumnInfo(columnTables[i], columnNames[i], columnSize[i]);
			MultiKey key = new MultiKey();
			key.setKey(columnTables[i]);
			key.setKey(columnNames[i]);
			map.put(key, i);
		}
	}

	/**
	 * Given the name of a column, returns the index in the respective tuple.
	 * 
	 * @param column column name
	 * @return index of column in tuple
	 */
	public int getIndex(final String column, final String table) {
		MultiKey key = new MultiKey();
		key.setKey(column);
		key.setKey(table);
		final Integer index = map.get(key);
		if (index == null) {
			return -1; // error
		} else {
			return index;
		}	
	}

	public int getSize(final String column, final String table) {
		int index = this.getIndex(column, table);
		return columnInfos[index].getSize();
	}
	
	public int getSize(final int index){
		return columnInfos[index].getSize();
	}
	
	public String getTableName(final int index){
		return columnInfos[index].getTableName();
	}
	
	public String getTableName(final String column, final String table){
		int index = this.getIndex(column, table);
		return columnInfos[index].getTableName();
	}
	
	public Integer[] getAllSize(){
		Integer[] result = new Integer[columnInfos.length];
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
	
	public String[] getAllTables(){
		String[] result = new String[columnInfos.length];
		for(int i=0; i<columnInfos.length; i++){
			result[i] = columnInfos[i].getTableName();
		}
		return result;
	}
	
	public int getOffset(String column, String table){
		int index = getIndex(column, table);
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
