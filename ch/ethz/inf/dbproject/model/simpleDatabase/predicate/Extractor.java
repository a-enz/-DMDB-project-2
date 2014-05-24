package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public interface Extractor {
	public abstract Value getValue(Tuple tuple);
}
