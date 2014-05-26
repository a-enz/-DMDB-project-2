package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import ch.ethz.inf.dbproject.model.simpleDatabase.operators.*;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.AllResultColumn;
import com.foundationdb.sql.parser.AndNode;
import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.CharConstantNode;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.ConstantNode;
import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.DeleteNode;
import com.foundationdb.sql.parser.FromTable;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.ResultColumn;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.UpdateNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;
import com.foundationdb.sql.types.TypeId;

public class RelVisitor implements Visitor{
	
	private boolean showNodes = false;

	@Override
	public Visitable visit(Visitable node) throws StandardException {
		if(node instanceof CursorNode) return visit((CursorNode) node);
		else if(node instanceof SelectNode) return visit((SelectNode) node);
		else if(node instanceof AndNode) return visit((AndNode) node);
		else if(node instanceof ConstantNode) return visit((ConstantNode) node);
		else if(node instanceof BinaryRelationalOperatorNode) return visit((BinaryRelationalOperatorNode) node);
		else if(node instanceof ColumnReference) return visit((ColumnReference) node);
		else if(node instanceof ResultColumnList) return visit((ResultColumnList) node);
		else if(node instanceof UpdateNode) {try {return visit((UpdateNode) node);} catch (FileNotFoundException e){return null;}}
		else if(node instanceof DeleteNode) {try {return visit((DeleteNode) node);} catch (FileNotFoundException e){return null;}}
		System.out.println("Visit NodeClass: " + node.getClass());
		return null;
	}
	
	public Visitable visit(DeleteNode node) throws FileNotFoundException, StandardException {
		Iterator<ResultColumn> cursor = node.getResultSetNode().getResultColumns().iterator();
		Project project = (Project) node.getResultSetNode().accept(this);
		ArrayList<String> values = new ArrayList<String>();
		
		while(cursor.hasNext()) {
			String tmp = cursor.next().getExpression().toString();
			System.out.println("value: " + tmp);
			values.add(tmp);
		}
		
		Delete delete = new Delete(project, project.getSchema().getAllNames(), project.getSchema().getAllTables(), values.toArray(new String[values.size()]));
		return delete;
	}
	
	public Visitable visit(ResultColumnList node) {
		return null;
	}
	
	public Visitable visit(UpdateNode node) throws FileNotFoundException, StandardException {
		Iterator<ResultColumn> cursor = node.getResultSetNode().getResultColumns().iterator();
		Project project = (Project) node.getResultSetNode().accept(this);
		ArrayList<String> values = new ArrayList<String>();
		
		while(cursor.hasNext()) {
			String tmp = cursor.next().getExpression().toString();
			System.out.println("value: " + tmp);
			values.add(tmp);
		}
		
		Update update = new Update(project, project.getSchema().getAllNames(), project.getSchema().getAllTables(), values.toArray(new String[values.size()]));
		return update;
	}
	
	
	public Visitable visit(ColumnReference node) {
		String table = node.getTableName();
		String column = node.getColumnName();
		int type;
		try {
			type = Helper.typeConvert(node.getTypeId());
		} catch (Exception e) {
			type = 0;
			//e.printStackTrace();
		}
		
		return new ColumnRef(table, column, type);
	}

	@Override
	public Visitable visit(CursorNode node) throws StandardException {
		return node.getResultSetNode().accept(this);
	}

	//magic happens here
	@Override
	public Visitable visit(SelectNode node) throws StandardException {
		
		System.out.println("SelectNode visited");
		Iterator<FromTable> cursor = node.getFromList().iterator();
		Iterator<ResultColumn> rCursor = node.getResultColumns().iterator();
		ResultColumn rCurrent;
		ArrayList<String> rColumns = new ArrayList<String>();
		ArrayList<String> rTables = new ArrayList<String>();
		String[] tmp;
		
		FromTable current;
		ArrayList<Scan>  scanList = new ArrayList<Scan>();
		Iterator<Scan> sCursor;
		Operator arg = null;
		
		try {
			while(cursor.hasNext()) {							//get all fromtables
				current = cursor.next();
				System.out.println(current.getOrigTableName().toString());
				scanList.add(new Scan(current.getOrigTableName().toString()));
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
		} catch(IOException e){
			e.printStackTrace();
		}
		
		while(rCursor.hasNext()) {								//generate projection clause
			rCurrent = rCursor.next();
			if (rCurrent instanceof AllResultColumn) {
				tmp = arg.getSchema().getAllColumnNamesByTable(((AllResultColumn) rCurrent).getTableName());
				for(int i = 0; i < tmp.length; i++) {
					rTables.add(((AllResultColumn) rCurrent).getTableName());
				}
			}
			rTables.add(rCurrent.getTableName());
			rColumns.add(rCurrent.getColumnName());
		}
		
		Predicate predicate = (Predicate) node.getWhereClause().accept(this);
		node.getResultColumns().accept(this);
		Select select = new Select(arg, predicate);
		Project project = new Project(select, rTables.toArray(new String[rTables.size()]), (String[]) rColumns.toArray(new String[rTables.size()]));
		return project;
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
	
	public Visitable visit(ConstantNode node) throws StandardException {
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
		return false;
	}

	@Override
	public boolean skipChildren(Visitable node) throws StandardException {
		return true;
	}

	private void showNode(Visitable node) {
		if(showNodes) System.out.println("Class: " + node.getClass().toString() + ", toString: " + node.toString());
	}
}
