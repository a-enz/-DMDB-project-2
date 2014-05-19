package mlei.operator;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Scan extends Operator {

	private TupleSchema schema;
	
	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveNext() {
		// TODO Auto-generated method stub
		return false;
	}

}
