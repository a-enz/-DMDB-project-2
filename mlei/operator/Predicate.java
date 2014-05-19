package mlei.operator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.parser.Visitable;

public interface Predicate extends Visitable{
	public boolean evaluate(Tuple tuple);
}
