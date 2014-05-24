package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import com.foundationdb.sql.parser.Visitable;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public interface Extractor extends Visitable{
	public abstract Value getValue(Tuple tuple);
}
