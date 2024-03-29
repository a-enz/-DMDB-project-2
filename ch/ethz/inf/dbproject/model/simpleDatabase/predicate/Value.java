package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Value implements Comparable<Value>, Printable{
	
	public static final int STRING = 0;
	public static final int INTEGER = 1;
	public static final int FLOAT = 2;
	public static final int DOUBLE = 3;
	public static final int DATE = 4;
	
	protected String val;
	protected String s;
	protected int n;
	protected float f;
	protected double d;
	protected Date da;
	
	protected int type;
	
	public Value(String val, int type) throws ParseException {
		SimpleDateFormat date = new SimpleDateFormat();
		this.val = val;
		switch (type) {
			case STRING:
				s = val;
				break;
			case INTEGER:
				n = Integer.parseInt(val);
				break;
			case FLOAT:
				f = Float.parseFloat(val);
				break;
			case DOUBLE:
				d = Double.parseDouble(val);
				break;
			case DATE:
				da = date.parse(val);
			default:
				s = val; 	//TODO throw da erroar
				break;
		}
		this.type = type;
		this.val = val;
	}
	
	public final int compareTo(Value val) {
		Double left = getNumerical();
		Double right = val.getNumerical();
		System.out.println("asdfasdf: " + left.compareTo(right)); 
		if(left != null && right != null)  {return left.compareTo(right);}		//numerical comparison
		else if(type == STRING && val.getType() == STRING) return s.compareTo(val.getString());		//string comparison
		else if(type == DATE && val.getType() == DATE) return da.compareTo(val.getDate());
		else return -1;		//TODO throw exception or something
	}
	
	public final boolean equals(Object other){
		Value otherval = (Value) other;
		return otherval.getVal().equals(this.getVal());
	}
	
	private String getVal(){
		return this.val;
	}
	
	private Date getDate() {
		return da;
	}

	public int getType() {
		return type;
	}
	
	public String getString() {
		return s;
	}


	public int getInteger() {
		return n;
	}


	public float getFloat() {
		return f;
	}


	public double getDouble() {
		return d;
	}
	
	/**
	 * wrapper function to get that damn compareTo to work...
	 * @return
	 * returns the value as double object if it represents a numerical value,
	 * returns null otherwise
	 */
	public Double getNumerical(){
		Double result;
		switch(type) {
			case STRING:
				result = null;
				break;
			case INTEGER:
				result = new Double(n);
				break;
			case FLOAT:
				result = new Double(f);
				break;
			case DOUBLE:
				result = new Double(d);
				break;
			case DATE:
				result = null;
			default:
				result = null;
				break;
		}
		return result;
	}

	@Override
	public void printTree(int depth) {
		String val = "";
		switch(type) {
			case STRING:
				val = s;
				break;
			case INTEGER:
				val = Integer.toString(n);
				break;
			case FLOAT:
				val = Float.toString(f);
				break;
			case DOUBLE:
				val = Double.toString(d);
				break;
			case DATE:
				val = da.toString();
				break;
			default:
				break;
		}
		System.out.println(Helper.indent(depth) + "ValueNode: " + val + ", Type: " + type);
	}
	
	public String toString() {
		return "Value: " + val + ", Type: " + type;
	}
}
