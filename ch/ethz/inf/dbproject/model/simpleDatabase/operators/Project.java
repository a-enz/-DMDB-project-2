package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.Helper;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class Project extends Operator {

	private final Operator op;
	private final String[] columns;
	private final String[] tables;
	private TupleSchema opSchema;

	private TupleSchema schema;
	private boolean first;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 */
	public Project(final Operator op, final String column, final String table)
	{
		this(op, new String[] { column }, new String[] { table });
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 */
	public Project(final Operator op, final String[] columns, final String[] tables) {
		this.op = op;
		this.columns = columns;
		this.tables = tables;

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
				
				Integer[] columnSize = new Integer[columns.length];
				String[] columnTable = new String[columns.length];
				Integer[] columnType = new Integer[columns.length];
				
				for(int i = 0; i < columns.length; i++){
					int index = opSchema.getIndex(columns[i], tables[i]);
					columnSize[i] = opSchema.getSize(index);
					columnTable[i] = opSchema.getTableName(index);
					columnType[i] = opSchema.getType(index);
				}

				this.schema = new TupleSchema(columns, columnSize, columnTable, columnType);
				first = false;
			}

			for (int i = 0; i < columns.length; i++){
				int index = opSchema.getIndex(columns[i], tables[i]); 
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
		return schema;
	}

	@Override
	public int getoffset() {
		return op.getoffset();
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "ProjectNode: ");
		
	}
}
