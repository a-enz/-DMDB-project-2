package ch.ethz.inf.dbproject.model.simpleDatabase;

public class ColumnInfo {

	private String tableName;
	private String columnName;
	private final int size;
	private final int type;
	
	/**
	 * @param args
	 */
	public ColumnInfo(String tableName, String columnName, int size, int type) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.size = size;
		this.type = type;
	}
	
	public int getType() {
		return type;
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
	  
	public int hashcode() {
		  String string = tableName + ":=:" + columnName;
		  return string.hashCode();
	}
	
    public boolean equals(Object other){
    	ColumnInfo othercolumn = (ColumnInfo) other;
    	if (this.tableName == othercolumn.tableName && this.columnName == othercolumn.columnName){
    		return true;
    	}
    	return false;
    }
}
