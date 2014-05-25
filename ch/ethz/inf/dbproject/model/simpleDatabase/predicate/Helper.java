package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

import com.foundationdb.sql.types.TypeId;

public class Helper {
	
	public static String indent(int depth){
		String res = "";
		for(int i = 0; i < depth; i++){
			res = res + "\t";
		}
		return res;
	}
	
	public static int typeConvert(TypeId id){
		if(id.isStringTypeId()) return 0;
		else if(id.isIntegerTypeId()) return 1;
		else if(id.isFloatingPointTypeId()) return 2;
		else if(id.isDoubleTypeId()) return 3;
		else if(id.isDateTimeTimeStampTypeId()) return 4;
		else return 0;
	}
}
