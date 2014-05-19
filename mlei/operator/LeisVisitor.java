package mlei.operator;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.AndNode;
import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

public class LeisVisitor implements Visitor{
	
	private boolean showNodes = false;

	@Override
	public Visitable visit(Visitable node) throws StandardException {
		if(node instanceof CursorNode) ((CursorNode) node).accept(this);
		return null;
	}
	

	@Override
	public Visitable visit(CursorNode node) throws StandardException {
		return node.getResultSetNode().accept(this);
	}

	@Override
	public Visitable visit(SelectNode node) throws StandardException {
		// TODO Auto-generated method stub
		node.getFromList().accept(this);
		node.getWhereClause().accept(this);
		Project project = new Project(); 
		return null;
	}
	
	public Visitable visit(AndNode node) throws StandardException {
		Predicate left = (Predicate) node.getLeftOperand().accept(this);
		Predicate right = (Predicate) node.getRightOperand().accept(this);
		Predicate result = new And(left, right);
		return result;
	}
	
	public Visitable visit(BinaryRelationalOperatorNode node) {
		int opType = node.getOperatorType();
		if(opType == BinaryRelationalOperatorNode.EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.NOT_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.GREATER_THAN_RELOP);
		else if(opType == BinaryRelationalOperatorNode.LESS_THAN_RELOP);
		else if(opType == BinaryRelationalOperatorNode.GREATER_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.LESS_EQUALS_RELOP);
		else if(opType == BinaryRelationalOperatorNode.IS_NULL_RELOP);
		else if(opType == BinaryRelationalOperatorNode.IS_NOT_NULL_RELOP);
		return null;
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
