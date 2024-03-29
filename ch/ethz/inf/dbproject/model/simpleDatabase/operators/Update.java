package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.*;
import java.io.*;

import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;
import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

/**
 * Projection in relational algebra. Returns tuples that contain on projected
 * columns. Therefore the new tuples conform to a new schema.
 */
public final class Update extends Operator implements Execute {

	private final Operator op;
	private final String[] columns;
	private final String[] tables;
	private final String[] values;
	private final String fileName;
	private final RandomAccessFile reader;
	private final TupleSchema schema;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param column single column name that will be projected
	 * @throws FileNotFoundException 
	 */
	public Update(final Operator op, final String columns, final String tables, final String[] values) throws FileNotFoundException
	{
		this(op, new String[] { columns }, new String[] { tables }, values);
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 * @throws FileNotFoundException 
	 */
	public Update(final Operator op, final String[] columns, final String[] tables, final String[] values) throws FileNotFoundException {
		this.op = op;
		this.columns = columns;
		this.tables = tables;
		this.fileName = op.getFileName();
		this.values = values;
		this.schema = op.getSchema();
		this.reader = new RandomAccessFile(new File(DBPATH + fileName), "rw");
	}

	@Override
	public boolean moveNext() throws IOException {
		boolean bool = op.moveNext();
		this.current = op.current;
		return bool;
	}
	
	public void doUpdate() throws IOException{
		while(moveNext()){
			int offset = op.getoffset();
			for (int i=0; i< columns.length; i++){
				int columnoffset = schema.getOffset(columns[i], tables[i]);
				reader.seek(offset + columnoffset);
				for (int j = 0; j < schema.getSize(columns[i],tables[i]); j++){
					reader.write((byte) 0x1b);
				}
				reader.seek(offset + columnoffset);
				reader.write(values[i].getBytes());
			}
		}
	}


	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void reset() throws IOException {
		System.out.println("Why do you want to reset an Update?");
		 throw new IOException();
	}

	@Override
	public TupleSchema getSchema() {
		return schema;
	}

	@Override
	public int getoffset() {
		return 0;
	}

	@Override
	public void printTree(int depth) {
		System.out.println("UpdateNode: Columns: " + Arrays.toString(columns) + ", Value: " + Arrays.toString(values));
		op.printTree(depth + 1);
	}

	@Override
	public void doExecute() {
		try {
			this.doUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
