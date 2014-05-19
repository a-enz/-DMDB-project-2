package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class Project extends Operator {

	private final Operator op;
	private final String[] columns;
	private TupleSchema opSchema;

	private TupleSchema schema;
	private boolean first;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 */
	public Project(final Operator op, final String column)
	{
		this(op, new String[] { column });
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 */
	public Project(final Operator op, final String[] columns) {
		this.op = op;
		this.columns = columns;

		this.first = true;
	}

	@Override
	public boolean moveNext() throws IOException {
		// TODO
		// get next tuple from child operator
		// create new tuple by copying the appropriate columns
		// return if we were able to advance to the next tuple
		List<String> result = new ArrayList<String>();
		
		if (op.moveNext()){

			if (first){
				this.opSchema = op.current.getSchema();
				
				Integer[] columnsize = new Integer[columns.length];
				
				int index = 0;
				for(String column:columns){
					columnsize[index] = opSchema.getSize(column);
				}

				this.schema = new TupleSchema(columns, columnsize);
				first = false;
			}

			for (String column:columns){
				int index = opSchema.getIndex(column); 
				if (index >= 0){
					result.add(op.current.get(index));
				}
			}
			current = new Tuple(this.schema, result.toArray(new String[result.size()]));
			return true;
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
		// TODO Auto-generated method stub
		return null;
	}
}
