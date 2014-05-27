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
public final class Delete extends Operator {

	private final Operator op;
	private final String fileName;
	private final RandomAccessFile reader;
	private final TupleSchema schema;
	/**
	 * Constructs a new projection operator.
	 * @param op operator to pull from
	 * @param columns column names that will be projected
	 * @throws FileNotFoundException 
	 */
	
	public Delete(final Operator op) throws FileNotFoundException {
		this.op = op;
		this.fileName = op.getFileName();
		this.schema = op.getSchema();
		this.reader = new RandomAccessFile(new File(DBPATH + fileName), "rw");
	}

	@Override
	public boolean moveNext() throws IOException {
		boolean bool = op.moveNext();
		this.current = op.current;
		return bool;
	}
	
	public void doDelete() throws IOException{
		while(moveNext()){
			int offset = op.getoffset();
			reader.seek(offset);
			for (int i = 0; i < blocksize; i++){
				reader.write((byte) 0x1b);
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
		// TODO Auto-generated method stub
		
	}
}
