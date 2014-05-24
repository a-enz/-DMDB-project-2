package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.*;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class GroupBy extends Operator {

	private final Operator op;
	private final String[] columns;
	private final TupleSchema schema;
	private HashMap<MultiKey, List<String>> map;
	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 */
	public GroupBy(final Operator op, final String column)
	{
		this(op, new String[] { column });
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 */
	public GroupBy(final Operator op, final String[] columns) {
		this.op = op;
		this.columns = columns;
		this.schema = op.getSchema();
	}

	@Override
	public boolean moveNext() throws IOException {
		// TODO
		// get next tuple from child operator
		// create new tuple by copying the appropriate columns
		// return if we were able to advance to the next tuple
		List<String> result = new ArrayList<String>();
		
		if (op.moveNext()){

		}
		return false;
	}

	@Override
	public String getFileName() {
		return op.getFileName();
	}

	@Override
	public void reset() throws IOException {
		op.reset();

	}

	@Override
	public TupleSchema getSchema() {
		return schema;
	}

	@Override
	public int getoffset() {
		return op.getoffset();
	}
}
