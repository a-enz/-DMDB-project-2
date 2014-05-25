package ch.ethz.inf.dbproject.model.simpleDatabase;

public class ColumnInfo {

	private String tableName;
	private String columnName;
	private final int size;
	
	/**
	 * @param args
	 */
	public ColumnInfo(String columnName, String tableName, int size) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.size = size;
	}
	
	public ColumnInfo(String columnName, String tableName) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.size = 0;
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
