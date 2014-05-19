package mlei.operator;

import java.util.ArrayList;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Project extends Operator{

	ArrayList<Tuple> from;
	
	
	@Override
	public boolean moveNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
