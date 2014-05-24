package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.AndNode;
import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.CharConstantNode;
import com.foundationdb.sql.parser.ConstantNode;
import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.FromTable;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.ResultColumn;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;
import com.foundationdb.sql.types.TypeId;

public class RelVisitor implements Visitor{
	
	private boolean showNodes = false;

	@Override
	public Visitable visit(Visitable node) throws StandardException {
		if(node instanceof CursorNode) ((CursorNode) node).accept(this);
		System.out.println("NodeClass: " + node.getClass());
		return null;
	}
	

	@Override
	public Visitable visit(CursorNode node) throws StandardException {
		return node.getResultSetNode().accept(this);
	}

	@Override
	public Visitable visit(SelectNode node) throws StandardException {
		
		Iterator<FromTable> cursor = node.getFromList().iterator();
		Iterator<ResultColumn> rCursor = node.getResultColumns().iterator();
		FromTable current;
		ArrayList<Scan>  scanList = new ArrayList<Scan>();
		Iterator<Scan> sCursor;
		Operator arg;
		
		while(cursor.hasNext()) {							//get all fromtables
			current = cursor.next();
			scanList.add(new Scan(current.getTableName().toString()));
		}
		
		if(scanList.size() > 1) {							//cross that shit if more than one source
			sCursor = scanList.iterator();
			Cross cross = new Cross(sCursor.next(), sCursor.next());
			while(sCursor.hasNext()) {
				cross = new Cross(cross, sCursor.next());
			}
			arg = cross;
		} else {
			arg = scanList.get(0);
		}
		
		
		Predicate predicate = (Predicate) node.getWhereClause().accept(this);
		node.getResultColumns().accept(this);
		Select select = new Select(arg, predicate);
		Project project = new Project();
		return null;
	}
	
	public Visitable visit(AndNode node) throws StandardException {
		Predicate left = (Predicate) node.getLeftOperand().accept(this);
		Predicate right = (Predicate) node.getRightOperand().accept(this);
		Predicate result = new And(left, right);
		return result;
	}
	
	public Visitable visit(BinaryRelationalOperatorNode node) throws StandardException {
		int opType = node.getOperatorType();
		Visitable res = null;
		if(opType == BinaryRelationalOperatorNode.EQUALS_RELOP) res = new Equals((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.NOT_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.GREATER_THAN_RELOP);
		else if(opType == BinaryRelationalOperatorNode.LESS_THAN_RELOP);
		else if(opType == BinaryRelationalOperatorNode.GREATER_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.LESS_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.IS_NULL_RELOP);
		else if(opType == BinaryRelationalOperatorNode.IS_NOT_NULL_RELOP);
		return res;
	}
	
	public Visitable visit(ConstantNode node) throws StandardException, ParseException {
		int type = 0;
		TypeId id = node.getTypeId();
		if (id.isStringTypeId()) type = 0;
		else if (id.isIntegerTypeId()) type = 1;
		else if (id.isFloatingPointTypeId()) type = 2;
		else if (id.isDoubleTypeId()) type = 3;
		else if (id.isDateTimeTimeStampTypeId()) type = 4;
		return new Constant(node.getValue().toString(), type);
	}
	
	@Override
	public boolean visitChildrenFirst(Visitable node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopTraversal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean skipChildren(Visitable node) throws StandardException {
		// TODO Auto-generated method stub
		return true;
	}

	private void showNode(Visitable node) {
		if(showNodes) System.out.println("Class: " + node.getClass().toString() + ", toString: " + node.toString());
	}
}
