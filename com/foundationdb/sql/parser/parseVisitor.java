package com.foundationdb.sql.parser;

import com.foundationdb.sql.StandardException;

public class parseVisitor implements Visitor
{
	
	
	
	@Override
	public Visitable visit(Visitable node) throws StandardException {
		System.out.println(node.toString());
		System.out.println("Class: " + node.getClass().toString());
		if (node instanceof CursorNode) {
			this.visit((CursorNode)node);
		}
		return null;
	}
	
	public Visitable visit(SelectNode node) throws StandardException {
		System.out.println("SELECT NODE: " + node.toString());
		op = node.accept(this);
		Select s = new Select(op,alskdjfalk);
		return s;
	}
	
	public Visitable visit(CursorNode node) throws StandardException {
		System.out.println("Cursor Node: " + node.accept(this));
		node.accept(this);
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
		return false;
	}
    
}
