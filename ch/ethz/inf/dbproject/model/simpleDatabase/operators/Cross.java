package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.util.*;
import java.lang.reflect.Array;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;


/**
 * The scan operator reads tuples from a file. The lines in the file contain the
 * values of a tuple. The line a comma separated.
 */
public class Cross extends Operator {

	private final Operator op1;
	private final Operator op2;	
	private final TupleSchema schema;
	
	private String read1[];
	private String read2[];
	
	private boolean op1next;

	/**
	 * Contructs a new scan operator.
	 * @param fileName file to read tuples from
	 */
//	public Cross(final String fileName) {
//		// read from file
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new FileReader(fileName));
//		} catch (final FileNotFoundException e) {
//			throw new RuntimeException("could not find file " + fileName);
//		}
//		this.reader = reader;
//		
//		// create schema
//		String[] columnNames;
//		try{
//			columnNames = reader.readLine().split(",");
//		} catch (final IOException e){
//			throw new RuntimeException("could not read: " + this.reader + 
//					". Error is " + e);
//		}
//		this.schema = new TupleSchema(columnNames);
//	}

	/**
	 * Constructs a new scan operator (mainly for testing purposes).
	 * @param reader reader to read lines from
	 * @param columns column names
	 * @throws IOException 
	 */

	
	public Cross(final Operator op1, final Operator op2) throws IOException
	{
		this.op1 = op1;
		this.op2 = op2;
		
		TupleSchema tuple1 = this.op1.getSchema();
		TupleSchema tuple2 = this.op2.getSchema();
		
		String[] columnNames = concat(tuple1.getAllNames(), tuple2.getAllNames());
		Integer[] columnSizes = concat(tuple1.getAllSize(), tuple2.getAllSize());

		
		this.schema = new TupleSchema(columnNames, columnSizes);
		
		op1next = op1.moveNext();
	}
	
	
	public static <T> T[] concat (T[] array1, T[] array2) {
		   int length1 = array1.length;
		   int length2 = array2.length;
		   
		   @SuppressWarnings("unchecked")
		   T[] ret = (T[]) Array.newInstance(array1.getClass().getComponentType(), length1 + length2); 
		   System.arraycopy(array1, 0, ret, 0, length1);
		   System.arraycopy(array2, 0, ret, length1, length2);
		   return ret;
		}

	@Override
	public boolean moveNext() throws IOException {
		if (op1.current == null && op1.moveNext() == false){
			return false;
		}
		
		if(op2.moveNext()){
			read1 = op1.current.getValue();
			read2 = op2.current.getValue();
			current = new Tuple(schema, concat(read1,read2));
			return true;
		}
		else{
			if(op1.moveNext()){
				op2.reset();
				if(op2.moveNext()){
					read1 = op1.current.getValue();
					read2 = op2.current.getValue();
					current = new Tuple(schema, concat(read1,read2));
					return true;
				}
			}
		}
		return false;
			
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public void reset() throws IOException {
		op1.reset();
		op2.reset();
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
