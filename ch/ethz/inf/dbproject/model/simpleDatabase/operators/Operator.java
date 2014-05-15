package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.*;

/**
 * Base class of all operators. An operator processes one tuple at a time. It
 * allows an application to call moveNext() to move to the next tuple. After
 * moveNext() the application can retrieve the new tuple by a call to current().
 */
public abstract class Operator implements Visitable{

	/**
	 * The current tuple.
	 */
	protected Tuple current;
	/**
	 * Moves forward to the next tuple. The next tuple can be retrieved by a
	 * call to current(). If there is no more tuple, this method returns false.
	 * @return true, if we advanced to next tuple
	 */
	public abstract boolean moveNext();
	
	public abstract String getFileName();

	/**
	 * @return the current tuple
	 */
	public final Tuple current() {
		return this.current;
	}

	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}
}
