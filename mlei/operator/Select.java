package mlei.operator;

import java.util.ArrayList;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class Select extends Operator{
	
	Predicate predicate;
	
	

	
	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveNext() {
		// TODO Auto-generated method stub
		// get next tuple, evaluate it with predicate: if true return it;
		while predicate.evaluate(current) 
		return false;
	}
	
}
