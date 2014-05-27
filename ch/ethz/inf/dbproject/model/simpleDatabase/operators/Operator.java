package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

import ch.ethz.inf.dbproject.model.simpleDatabase.*;
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.Printable;

/**
 * Base class of all operators. An operator processes one tuple at a time. It
 * allows an application to call moveNext() to move to the next tuple. After
 * moveNext() the application can retrieve the new tuple by a call to current().
 */
public abstract class Operator implements Visitable, Printable{
	/**
	 * The current tuple.
	 */
	protected Tuple current;
	protected TupleSchema schema;
	
	protected int blocksize = 1024;
	protected int headerblock = 3;
	protected String DBPATH = "/home/daniel/Documents/DMDB/";
	protected final String EXTENSION = ".txt";
	
	/**
	 * Moves forward to the next tuple. The next tuple can be retrieved by a
	 * call to current(). If there is no more tuple, this method returns false.
	 * @return true, if we advanced to next tuple
	 * @throws IOException 
	 */
	public abstract boolean moveNext() throws IOException;
	
	public abstract String getFileName();
	
	public abstract void reset() throws IOException;
	
	public TupleSchema getSchema(){
		return schema;
	}

	public abstract int getoffset();

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
