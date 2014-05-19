package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import com.foundationdb.sql.parser.Visitable;

public abstract class BinaryOp {
	protected Predicate left;
	protected Predicate right;
	
	public Predicate getLeft(){
		return left;
	}
	
	public Predicate getRight(){
		return right;
	}
}
