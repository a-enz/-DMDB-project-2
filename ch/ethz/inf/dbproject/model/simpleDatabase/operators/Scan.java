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
import ch.ethz.inf.dbproject.model.simpleDatabase.predicate.Helper;


/**
 * The scan operator reads tuples from a file. The lines in the file contain the
 * values of a tuple. The line a comma separated.
 */
public class Scan extends Operator {
	
	private final RandomAccessFile reader;
	private final TupleSchema schema;
	private final String fileName;
	private final String tableName;
	private byte[] buffer;
	private final String DBPATH = "/home/mlei/workspace/database/";
	private final String EXTENSION = ".txt";
	private int offset;
	

	/**
	 * Contructs a new scan operator.
	 * @param fileName file to read tuples from
	 * @throws IOException 
	 */
	public Scan(final String tableName) throws IOException {
		// read from file
		fileName = tableName + EXTENSION;
		RandomAccessFile reader = null;
		try {
			reader = new RandomAccessFile (DBPATH + fileName, "rw");
		} catch (final FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
			throw new RuntimeException("could not find file " + fileName);
		}
		this.reader = reader;
		this.tableName = tableName;
		this.buffer = new byte[blocksize];
		// create schema
		String[] columnNames;
		try{
			reader.read(buffer);
			offset += blocksize;
			columnNames = parseLine(buffer).split(",");
		} catch (final IOException e){
			throw new RuntimeException("could not read column name: " + this.reader + 
					". Error is " + e);
		}
		
		String[] columnSize;
		try{
			reader.read(buffer);
			offset += blocksize;
			columnSize = parseLine(buffer).split(",");
		} catch (final IOException e){
			throw new RuntimeException("could not read column size: " + this.reader + 
					". Error is " + e);
		}
		
		String[] tableNames = new String[columnSize.length];
		
		for (int i = 0; i < tableNames.length; i++){
			tableNames[i] = this.tableName;
		}
		
		String[] columnType;
		try{
			reader.read(buffer);
			offset += blocksize;
			columnType = parseLine(buffer).split(",");
		} catch (final IOException e){
			throw new RuntimeException("could not read column type: " + this.reader + 
					". Error is " + e);
		}

		this.schema = new TupleSchema (columnNames, columnSize, tableNames, columnType);
		
		//========== For testing purposes=============
		
//		for (String s:columnNames){
//			System.out.println("columnName: " + s);
//		}
		
//		for (String s:tableNames){
//		System.out.println("columnTable: " + s);
//	}
//		
//		for (String s:columnSize){
//		System.out.println("columnSizes: " + s);
//	}
		
//		for (String s:columnType){
//		System.out.println("columnTypes: " + s);
//	}
		
		//============================================
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
	    return cbuf.toString().toLowerCase();
	  }

	/**
	 * Constructs a new scan operator (mainly for testing purposes).
	 * @param reader reader to read lines from
	 * @param columns column names
	 */


	@Override
	public boolean moveNext() {
		
//		old version
//		===========
		
//		while (reader.getFilePointer() + 1024 < reader.length()){
//			reader.read(buffer);
//			offset += blocksize;
//			String[] schemaValue = parseBuffer(buffer);
//			this.current = new Tuple(this.schema, schemaValue);
//			return true;
//		}
//		return false;
		
		
		//TODO: we just need to check the first bit (the valid flag!) 
		
		try {
			while (reader.getFilePointer() + 1024 < reader.length()){
				reader.read(buffer);
				
				
				offset += blocksize;
				String[] schemaValue = parseBuffer(buffer);
				
//				for (String s:schemaValue){
//					System.out.println("SchemaValue: " + s);
//				}
				

				boolean notnullschema = false;
				
				for (String s:schemaValue){
					if(s.length() != 0){
						notnullschema = true;
						break;
					}
				}
				
				if (notnullschema){
					this.current = new Tuple(this.schema, schemaValue);
					return true;
				}
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

	public String[] parseBuffer(byte[] buffer){
		int length = this.schema.getAllSize().length;
		String[] ret = new String[length];
		StringBuilder cbuf = new StringBuilder();
		int offset = 0;
		
		for(int i=0; i<length;i++){
			int j=0;
			int bound = schema.getSize(i);
			while(j<bound){
				byte b = buffer[offset+j];
				if (!(b == 0x1b)) {
			        cbuf.append((char) b);
			      }else{
			    	  break;
			      }
				j++;
			}
	    	ret[i] = cbuf.toString();
	    	cbuf.delete(0, cbuf.length());
			offset += bound;
		}
		return ret;
	}

	@Override
	public Visitable accept(Visitor v) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void reset() throws IOException {
		reader.seek(headerblock*blocksize);
		offset = headerblock*blocksize;
	}

	@Override
	public TupleSchema getSchema() {
		return schema;
	}

	@Override
	public int getoffset() {
		return offset;
	}

	@Override
	public void printTree(int depth) {
		System.out.println(Helper.indent(depth) + "ScanNode: " + fileName);
		
	}

}
