package ch.ethz.inf.dbproject.model.simpleDatabase.predicate;

public class Helper {
	public static String indent(int depth){
		String res = "";
		for(int i = 0; i < depth; i++){
			res = res + "\t";
		}
		return res;
	}
}
