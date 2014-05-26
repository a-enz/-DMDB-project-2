package ch.ethz.inf.dbproject.myDatabase;

import ch.ethz.inf.dbproject.myDatabase.*;

import java.util.*;
import java.lang.StringBuilder;


public class Statement{
	
	private ResultSet result;
	private List<String> column;
	
	public Statement(){
	 column = new ArrayList<String>();
	}
	
	public ResultSet executeQuery(String statement){
		visit(statement);
		return result;
	}
	
	public void visit(String statement){
		StringBuilder statementBuilder = new StringBuilder(statement);
		String word = getNextWordSpace(new StringBuilder(statementBuilder));
		if (word.equalsIgnoreCase("SELECT")){
			//TODO: parse all the column
		}
		else{
			throw new IllegalArgumentException( "Couldn't find a SELECT clause." );
		}
		if (word.equalsIgnoreCase("FROM")){
		}
		//UPDATE
		//DELETE
	}
	
	public ResultSet getResultSet(){
		return result;
	}
	
	public List<String> getColumn(){
		return column;
	}
	
	public String getNextWordSpace(StringBuilder string){
		int i = string.indexOf(" ");
		String word;
		if (i>0){
			word = string.substring(0, i);
			string.delete(0, i+1);
		}
		else{
			word = string.toString();
			string.delete(0, string.length());
		}
		return word;
	}
	
	public void setcolumn(StringBuilder string){
		String word = getNextWordSpace(string);
		while(word.charAt(word.length()-1) == ','){
			column.add(word.substring(0,word.length()-1));
			word = getNextWordSpace(string);
		}
		column.add(word);
	}
	
	//TODO implement all of the below
	public int executeUpdate(String statement){
		return 0;
	}
	
	public void close(){
		
	}
	
	public boolean execute(String statement){
		return false;
	}
	
	public void addBatch(String statement){
		
	}
	
	public int[] executeBatch(){
		return null;
	}
}
