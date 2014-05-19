package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;


/**
 * The scan operator reads tuples from a file. The lines in the file contain the
 * values of a tuple. The line a comma separated.
 */
public class Scan extends Operator {
	
	private final RandomAccessFile reader;
	private final TupleSchema schema;
	private final String fileName;
	private byte[] buffer;
	
	private int blocksize = 1024;

	
	/**
	 * Contructs a new scan operator.
	 * @param fileName file to read tuples from
	 */
	public Scan(final String fileName) {
		// read from file
		RandomAccessFile reader = null;
		try {
			reader = new RandomAccessFile (fileName, "rw");
		} catch (final FileNotFoundException e) {
			throw new RuntimeException("could not find file " + fileName);
		}
		this.reader = reader;
		this.fileName = fileName;
		this.buffer = new byte[blocksize];
		// create schema
		String[] columnNames;
		try{
			reader.read(buffer);
			columnNames = parseLine(buffer).split(",");
		} catch (final IOException e){
			throw new RuntimeException("could not read column name: " + this.reader + 
					". Error is " + e);
		}
		
		String[] columnSize;
		try{
			reader.read(buffer);
			columnSize = parseLine(buffer).split(",");
			System.out.println(buffer);
		} catch (final IOException e){
			throw new RuntimeException("could not read column size: " + this.reader + 
					". Error is " + e);
		}
		
		for(String name:columnNames){
			System.out.println("columnName " + name);
		}
		for(String size:columnNames){
			System.out.println("columnSize " + size);
		}
		this.schema = new TupleSchema(columnNames,columnSize);
	}
	
	public static String parseLine(byte[] data) {
	    StringBuilder cbuf = new StringBuilder();
	    for (byte b : data) {
	      if (!(b == 0x1b)) {
	        cbuf.append((char) b);
	      }else{
	    	  break;
	      }
	    }
	    return cbuf.toString();
	  }

	/**
	 * Constructs a new scan operator (mainly for testing purposes).
	 * @param reader reader to read lines from
	 * @param columns column names
	 */

	@Override
	public boolean moveNext() {
		
		try {
			String read = reader.readLine();
			if (read != null){				
				this.current = new Tuple(this.schema, read.split(","));
				return true;
			}
			return false;
			
			// TODO
			// a) read next line
			// b) check if we are at the end of the file (line would be null)
			//   b1. if we reached end of the file, close the buffered reader
			// c) split up comma separated values
			// d) create new tuple using schema and values
		} catch (final IOException e) {			
			throw new RuntimeException("could not read: " + this.reader + 
				". Error is " + e);
		}		
	}
	
	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void reset() throws IOException {
		reader.seek(blocksize);
	}
}
