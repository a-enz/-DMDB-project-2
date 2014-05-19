package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.util.*;
import java.io.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class Update extends Operator {

	private final Operator op;
	private final String[] columns;
	private final String[] strings;
	private final String fileName;
	private final RandomAccessFile reader;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 * @throws FileNotFoundException 
	 */
	public Update(final Operator op, final String columns, final String[] strings) throws FileNotFoundException
	{
		this(op, new String[] { columns }, strings);
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 * @throws FileNotFoundException 
	 */
	public Update(final Operator op, final String[] columns, final String[] strings) throws FileNotFoundException {
		this.op = op;
		this.columns = columns;
		this.fileName = op.getFileName();
		this.strings = strings;
		
		this.reader = new RandomAccessFile(new File(fileName), "rw");
	}

	@Override
	public boolean moveNext() {
		boolean bool = op.moveNext();
		this.current = op.current;
		return bool;
	}

	@Override
	public String getFileName() {
		return null;
	}
}
