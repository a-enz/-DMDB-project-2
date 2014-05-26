package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;


import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.parser.Visitable;

public interface Predicate extends Visitable, Printable{
	public boolean evaluate(Tuple tuple);
}
