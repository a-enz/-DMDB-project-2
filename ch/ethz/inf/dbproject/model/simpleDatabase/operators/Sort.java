package ch.ethz.inf.dbproject.model.simpleDatabase.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import ch.ethz.inf.dbproject.model.simpleDatabase.Tuple;

/**
 * An empty sort operator
 * For the purposes of the project, you can consider only string comparisons of
 * the values.
 */
public class Sort extends Operator implements Comparator<Tuple> {

	private final Operator op;
	private final String column;
	private final boolean ascending;
	private final ArrayList<Tuple> sortBuffer;
	private int offset;
	
	public Sort(final Operator op,final String column,final boolean ascending) {
		this.op = op;
		this.column = column;
		this.ascending = ascending;
		this.sortBuffer = new ArrayList<Tuple>();
	}
	
	@Override
	public final int compare(final Tuple l, final Tuple r) {
		
		final int columnIndex = l.getSchema().getIndex(this.column);
		
		final int result = 
			l.get(columnIndex).compareToIgnoreCase(r.get(columnIndex));
		
		if (this.ascending) {
			return result;
		} else {
			return -result;
		}
	}

	@Override
	public boolean moveNext() {

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

	
}
