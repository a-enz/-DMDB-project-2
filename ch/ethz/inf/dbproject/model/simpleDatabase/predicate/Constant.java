package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

public class Constant implements Extractor{

	private Value value;
	
	public Constant(String val, int type){
		value = new Value(val, type);
	}
	
	@Override
	public Value getValue(Tuple tuple) {
		return value;
	}

}
