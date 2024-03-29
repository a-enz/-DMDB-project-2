package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.foundationdb.sql.parser.InsertNode;
import com.foundationdb.sql.parser.NotNode;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.OrNode;
import com.foundationdb.sql.parser.OrderByColumn;
import com.foundationdb.sql.parser.OrderByList;
import com.foundationdb.sql.parser.ResultColumn;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.SubqueryNode;
import com.foundationdb.sql.parser.UpdateNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;
import com.foundationdb.sql.types.TypeId;

public class RelVisitor implements Visitor{
	
	private boolean isDelete;
	private boolean showNodes = false;

	@Override
	public Visitable visit(Visitable node) throws StandardException {
		if(node instanceof CursorNode) return visit((CursorNode) node);
		else if(node instanceof SelectNode) return visit((SelectNode) node);
		else if(node instanceof AndNode) return visit((AndNode) node);
		else if(node instanceof OrNode) return visit((OrNode) node);
		else if(node instanceof ConstantNode) return visit((ConstantNode) node);
		else if(node instanceof BinaryRelationalOperatorNode) return visit((BinaryRelationalOperatorNode) node);
		else if(node instanceof ColumnReference) return visit((ColumnReference) node);
		else if(node instanceof ResultColumnList) return visit((ResultColumnList) node);
		else if(node instanceof SubqueryNode)  {try {return visit((SubqueryNode) node);} catch (Exception e){e.printStackTrace(); return null;}}
		else if(node instanceof NotNode)  {try {return visit((NotNode) node);} catch (IOException e){e.printStackTrace(); return null;}}
		else if(node instanceof UpdateNode) {try {return visit((UpdateNode) node);} catch (Exception e){e.printStackTrace(); return null;}}
		else if(node instanceof DeleteNode) {try {return visit((DeleteNode) node);} catch (Exception e){e.printStackTrace(); return null;}}
		else if(node instanceof InsertNode) {try {return visit((InsertNode) node);} catch (FileNotFoundException e){return null;} catch (IOException e) {return null;}}
		//else if(node instanceof )

		System.out.println("Visit NodeClass: " + node.getClass());
		return null;
	}
	
	public Visitable visit(DeleteNode node) throws StandardException, IOException {
//		Iterator<ResultColumn> cursor = node.getResultSetNode().getResultColumns().iterator();
//		Project project = (Project) node.getResultSetNode().accept(this);
//		ArrayList<String> values = new ArrayList<String>();
//		
//		while(cursor.hasNext()) {
//			String tmp = cursor.next().getExpression().toString();
//			System.out.println("value: " + tmp);
//			values.add(tmp);
//		}
		//System.out.println("Jeeesus");
		SelectNode selectNode = (SelectNode) node.getResultSetNode();
		Predicate pred = (Predicate) selectNode.getWhereClause().accept(this);
		Scan scan = new Scan(selectNode.getFromList().get(0).getOrigTableName().toString());
		Select select = new Select(scan, pred);
		
		Delete delete = new Delete(select);
		return delete;
	}
	
	public Visitable visit(InsertNode node) throws FileNotFoundException, IOException{
		String tableName = node.getTargetTableName().getTableName();
		ResultColumnList columns = node.getTargetColumnList(); //ResultColumn -reference-> columnReference -columnName-> columnName
		ResultColumnList values = node.getResultSetNode().getResultColumns(); //ResultColumn -expression-> CharConstantNode -value-> Value
		ArrayList<String> columnInsert = new ArrayList<String>();
		ArrayList<String> valueInsert = new ArrayList<String>();
		
		for (ResultColumn column : columns){
			columnInsert.add(column.getReference().getColumnName());
			System.out.println("ColumnName: " + column.getReference().getColumnName());
		}
		
		for (ResultColumn value : values){
			ConstantNode constant = (ConstantNode)value.getExpression();
			valueInsert.add(constant.getValue().toString());
			System.out.println("Values: " + constant.getValue().toString());
		}
		
		Insert insert = new Insert(new Scan(tableName),columnInsert.toArray(new String[columnInsert.size()]), valueInsert.toArray(new String[valueInsert.size()]));
		return insert;
	}
	
	public Visitable visit(ResultColumnList node) {
		return null;
	}
	
	public Visitable visit(UpdateNode node) throws StandardException, IOException {
//		Iterator<ResultColumn> cursor = node.getResultSetNode().getResultColumns().iterator();
//		System.out.println("Res: " + node.getResultSetNode().getResultColumns().get(0).toString());
//		Project project = (Project) node.getResultSetNode().accept(this);
//		ArrayList<String> values = new ArrayList<String>();
//		
//		while(cursor.hasNext()) {
//			String tmp = cursor.next().getExpression().toString();
//			System.out.println("value: " + tmp);
//			values.add(tmp);
//		}
//		
//		Update update = new Update(project, project.getSchema().getAllNames(), project.getSchema().getAllTables(), values.toArray(new String[values.size()]));
//		return update;
		
		Iterator<ResultColumn> cursor = node.getResultSetNode().getResultColumns().iterator();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> tables = new ArrayList<String>();
		ResultColumn current;
		
		while(cursor.hasNext()) {
			current = cursor.next();
			ConstantNode constant = (ConstantNode) current.getExpression();
			String column = current.getReference().getColumnName();
			String table = current.getReference().getTableName();
			System.out.println("value: " +constant.getValue().toString());
			System.out.println("column: " + column);
			System.out.println("table: " + table);
			values.add(constant.getValue().toString());
			columns.add(column);
			tables.add(table);
		}
		
		SelectNode selectNode = (SelectNode) node.getResultSetNode();
		Predicate pred = (Predicate) selectNode.getWhereClause().accept(this);
		Scan scan = new Scan(selectNode.getFromList().get(0).getOrigTableName().toString());
		Select select = new Select(scan, pred);
		
		return new Update(select, columns.toArray(new String[columns.size()]),  tables.toArray(new String[tables.size()]), values.toArray(new String[values.size()]));
		
	}
	
	public Visitable visit(NotNode node) throws IOException, StandardException {
		System.out.println("not: " + node.getOperand());
		//System.out.println(node.);
		return new Not((Predicate)node.getOperand().accept(this));
	}
	
	public Visitable visit(SubqueryNode node) throws StandardException, IOException, ParseException {
		System.out.println("InNode: " + node.getLeftOperand());
		return new In((Operator)node.getResultSet().accept(this), (Extractor) node.getLeftOperand().accept(this));
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
		
		return new ColumnRef(column, table);
	}


	@Override
	public Visitable visit(CursorNode node) throws StandardException {
		OrderByList obl = node.getOrderByList();
		if(obl == null) return node.getResultSetNode().accept(this);
		else {
			System.out.println("OrderBy: " + obl.get(0).getExpression().getColumnName() + ", " + obl.get(0).getExpression().getTableName());
			return new Sort((Operator) node.getResultSetNode().accept(this), obl.get(0).getExpression().getColumnName(), obl.get(0).getExpression().getTableName());
		}
	}

	//magic happens here
	@Override
	public Visitable visit(SelectNode node) throws StandardException {
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
				scanList.add(new Scan(current.getOrigTableName().toString(), current.getCorrelationName()));
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
		
		//=============================================================
		//TODO: this has to be fixed:
		// use rCurrent.getExpression.getTableName instead of rCurrent.getTableName
		// for getColumnName the same
		//==============================================================
		
		while(rCursor.hasNext()) {//generate projection clause
			rCurrent = rCursor.next();
			if (rCurrent instanceof AllResultColumn) {
				tmp = arg.getSchema().getAllColumnNamesByTable(((AllResultColumn) rCurrent).getFullTableName());
				for(String s : tmp){
					rColumns.add(s);
				}
				//System.out.println("Schema: " + )
				System.out.println("tableName: " + ((AllResultColumn) rCurrent).getFullTableName() + ", " + Arrays.toString(tmp));
				for(int i = 0; i < tmp.length; i++) {
					rTables.add(((AllResultColumn) rCurrent).getFullTableName());
				}
			} else {
				rTables.add(rCurrent.getExpression().getTableName());
				rColumns.add(rCurrent.getExpression().getColumnName());
			}
		}
		Visitable where = node.getWhereClause();
		Predicate predicate;
		if(where != null) {		
			predicate = (Predicate) where.accept(this);
		} else {
			predicate = new Tautology();
		}
		node.getResultColumns().accept(this);
		Select select = new Select(arg, predicate);
		System.out.println("Columns: " + rColumns.toString()+ ", Tables: " + rTables.toString());
		Project project = new Project(select, rColumns.toArray(new String[rTables.size()]), rTables.toArray(new String[rTables.size()]));
		return project;
	}
	
	public Visitable visit(AndNode node) throws StandardException {
		Predicate left = (Predicate) node.getLeftOperand().accept(this);
		Predicate right = (Predicate) node.getRightOperand().accept(this);
		Predicate result = new And(left, right);
		return result;
	}
	
	public Visitable visit(OrNode node) throws StandardException {
		Predicate left = (Predicate) node.getLeftOperand().accept(this);
		Predicate right = (Predicate) node.getRightOperand().accept(this);
		Predicate result = new Or(left, right);
		return result;
	}
	
	public Visitable visit(BinaryRelationalOperatorNode node) throws StandardException {
		int opType = node.getOperatorType();
		Visitable res = null;
		if(opType == BinaryRelationalOperatorNode.EQUALS_RELOP) res = new Equals((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.NOT_EQUALS_RELOP) res = new NotEquals((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.GREATER_THAN_RELOP) res = new Greater((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.LESS_THAN_RELOP) res = new Less((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.GREATER_EQUALS_RELOP) res = new GreaterEquals((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
		else if(opType == BinaryRelationalOperatorNode.LESS_EQUALS_RELOP) res = new LessEquals((Extractor) node.getLeftOperand().accept(this), (Extractor) node.getRightOperand().accept(this));
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
