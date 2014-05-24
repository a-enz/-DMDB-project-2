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
public final class Update extends Operator {

	private final Operator op;
	private final String[] columns;
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
	public Update(final Operator op, final String columns, final String[] values) throws FileNotFoundException
	{
		this(op, new String[] { columns }, values);
	}

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 * @throws FileNotFoundException 
	 */
	public Update(final Operator op, final String[] columns, final String[] values) throws FileNotFoundException {
		this.op = op;
		this.columns = columns;
		this.fileName = op.getFileName();
		this.values = values;
		this.schema = op.getSchema();
		this.reader = new RandomAccessFile(new File(fileName), "rw");
	}

	@Override
	public boolean moveNext() throws IOException {
		boolean bool = op.moveNext();
		this.current = op.current;
		return bool;
	}
	
	public void doUpdate() throws IOException{
		while(moveNext()){
			//TODO: get Offset
			int offset = 0; //this has to be the offset
			for (int i=0; i< columns.length; i++){
				int columnoffset = schema.getOffset(columns[i]);
				reader.write(values[i].getBytes(), offset + columnoffset, schema.getSize(columns[i]));
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
}
