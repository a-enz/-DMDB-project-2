package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.util.*;
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

	private final BufferedReader reader1;
	private final BufferedReader reader2;	
	private String read1;
	private String read2;
	final private TupleSchema schema;

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
	 */

	
	public Cross(final Reader reader1, final Reader reader2, int lengthReader2) {
		this.reader1 = new BufferedReader(reader1);
		this.reader2 = new BufferedReader(reader2);
		
		String columnName1;
		String columnName2;
		try{
			columnName1 = this.reader1.readLine();
		} catch (final IOException e){
			throw new RuntimeException("could not read: " + this.reader1 + ". Error is " + e);
		}

		try{
			columnName2 = this.reader2.readLine();
			this.reader2.mark(lengthReader2);
		} catch (final IOException e){
			throw new RuntimeException("could not read: " + this.reader2 + ". Error is " + e);
		}
		
		String columnName12 = columnName1+","+columnName2;
		this.schema = new TupleSchema(columnName12.split(","));
	}

	@Override
	public boolean moveNext() {
		
		try {
			if (read1 == null){
				read1 = this.reader1.readLine();
			}
			
			read2 = this.reader2.readLine();

			if (read2 == null){
				reader2.reset();
				
				read1 = this.reader1.readLine();
				read2 = this.reader2.readLine();
				
				if (read1 == null){
					return false;
				}
			}	
			
			String result = read1 + "," + read2;
			this.current = new Tuple(this.schema,result.split(","));
			return true;

		} catch (final IOException e) {			
			throw new RuntimeException("could not read: " + this.reader1 + 
				". Error is " + e);
		}		
	}

}
