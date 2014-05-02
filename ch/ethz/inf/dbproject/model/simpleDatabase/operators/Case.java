package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.util.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class Case extends Operator {

	private final Operator op;
	private final String[] columns;
	private final TupleSchema newSchema;
	private final TupleSchema opSchema;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 */
	public Case(final Operator op, final String column)
	{
		this(op, new String[] { column });
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 */
	public Case(final Operator op, final String[] columns) {
		this.op = op;
		this.columns = columns;
		this.newSchema = new TupleSchema(columns);
		this.opSchema = op.current.getSchema();
	}

	@Override
	public boolean moveNext() {
		// TODO
		// get next tuple from child operator
		// create new tuple by copying the appropriate columns
		// return if we were able to advance to the next tuple
		List<String> result = new ArrayList<String>();
		
		if (op.moveNext()){
			for (String column:columns){
				if (opSchema.getIndex(column) > 0){
					result.add(column);
				}
			}
			return true;
		}
		return false;
	}
}
