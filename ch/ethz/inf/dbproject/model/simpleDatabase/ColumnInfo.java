package ch.ethz.inf.dbproject.model.simpleDatabase;

public class ColumnInfo {

	private String tableName;
	private String columnName;
	private final int size;
	private final int type;
	
	/*
	Type Codes
	==========
	STRING = 0;
	INTEGER = 1;
	FLOAT = 2;
	DOUBLE = 3;
	DATE = 4;
	*/
	
	/**
	 * @param args
	 */

	public ColumnInfo(String columnName, String tableName, int size, int type) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.size = size;
		this.type = type;
	}
	

	public ColumnInfo(String columnName, String tableName) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.size = 0;
		this.type = 0;
	}

	public String getTableName(){
		return tableName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getType(){
		return type;
	}
	  
	public int hashCode() {
		  String string = tableName + ":=:" + columnName;
		  return string.hashCode();
	}
	
    public boolean equals(Object other){
    	ColumnInfo othercolumn = (ColumnInfo) other;
    	if (this.tableName.equals(othercolumn.getTableName()) && this.columnName.equals(othercolumn.getColumnName())){
    		return true;
    	}
    	return false;
    }
}
