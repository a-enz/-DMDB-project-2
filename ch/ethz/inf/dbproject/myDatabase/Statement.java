package ch.ethz.inf.dbproject.myDatabase;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.Project;
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.RelVisitor;
import ch.ethz.inf.dbproject.myDatabase.*;

import java.util.*;
import java.lang.StringBuilder;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import com.foundationdb.sql.parser.Visitable;


public class Statement{
	
	private SQLParser parser;
	private RelVisitor visitor;
	private StatementNode node;
	private Project project;
	private Visitable visit;
	
	public ResultSet executeQuery(String statement) throws StandardException{
		parser = new SQLParser();
		node = parser.parseStatement(statement);
		visitor = new RelVisitor();
		visit = visitor.visit(node);
		project = (Project) visit;
		return new ResultSet(project);
	}
	
	
	public void executeUpdate(String statement){
		//TODO: to implement
	}
	
	public void close(){
		parser = null;
		visitor = null;
		node = null;
		project = null;
		visit = null;
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
