package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;
import ch.ethz.inf.dbproject.model.simpleDatabase.TupleSchema;

/**
 * An empty sort operator
 * For the purposes of the project, you can consider only string comparisons of
 * the values.
 */
public class Sort extends Operator implements Comparator<Tuple> {

	private final Operator op;
	private final String column;
	private final ArrayList<Tuple> sortBuffer;
	private int offset;
	private final String table;
	
	public Sort(final Operator op,final String column, final String table) {
		this.op = op;
		this.column = column;
		this.sortBuffer = new ArrayList<Tuple>();
		this.table = table;
	}
	
	@Override
	public final int compare(final Tuple l, final Tuple r) {
		
		final int columnIndex = l.getSchema().getIndex(this.column, this.table);
		
		final int result = 
			l.get(columnIndex).compareToIgnoreCase(r.get(columnIndex));
		
		return result;
	}

	@Override
	public boolean moveNext() throws IOException {

		// TODO 
		
		// a) if this is the first call:
		//   1) fetch _all_ tuples from this.op and store them in sort buffer
		//   2) sort the buffer
		if (this.current == null){
			if (this.op.moveNext()){
				this.sortBuffer.add(this.op.current);
			}
			this.offset = 0;
			Collections.sort(this.sortBuffer, this);
			this.current = this.sortBuffer.get(this.offset);
			return true;
		}
		else
		{
			this.offset += 1;
			if (offset < sortBuffer.size()){
				this.current = this.sortBuffer.get(this.offset);
				return true;
			}
		}
		//   3) set the current tuple to the first one in the sort buffer and 
		//      remember you are at offset 0
		// b) if this is not the first call 
		//   1) increase the offset and if it is valid fetch the next tuple
		
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
		return op.getSchema();
	}

	@Override
	public int getoffset() {
		return op.getoffset();
	}

	@Override
	public void printTree(int depth) {
		// TODO Auto-generated method stub
		
	}

	
}
