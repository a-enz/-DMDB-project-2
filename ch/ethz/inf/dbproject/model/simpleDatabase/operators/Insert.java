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
public final class Insert extends Operator {
	
	private final String[] columns;
	private final String table;
	private final String[] values;
	private final String fileName;
	private final RandomAccessFile reader;
	private final TupleSchema schema;

	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 * @throws FileNotFoundException 
	 */
	public Insert(final Operator op, final String[] columns, final String[] values) throws FileNotFoundException {
		this.columns = columns;
		this.fileName = op.getFileName();
		this.table = this.fileName.substring(0, this.fileName.indexOf("."));
		this.values = values;
		this.schema = op.getSchema();
		this.reader = new RandomAccessFile(new File(DBPATH + fileName), "rw");
	}

	@Override
	public boolean moveNext() throws IOException {
		return false;
	}
	
	public void doInsert() throws IOException{
		long offset = reader.length();
		reader.seek(offset);
		for (int i = 0; i < blocksize; i++){
			reader.write((byte) 0x1b);
		}
		int index = 0;
		for (String column : columns){
			int columnoffset = schema.getOffset(column, "");
			reader.seek(offset + columnoffset);
			reader.writeBytes(values[index]);
			index++;
		}
	}


	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void reset() throws IOException {
		 throw new IOException("Error: Why do you want to reset an Insert?");
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
		// TODO Auto-generated method stub
		
	}
}
