package mlei.operator;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public abstract class Operator implements Visitable{

	@Override
	public abstract Visitable accept(Visitor v) throws StandardException;
	public abstract boolean moveNext();

}
