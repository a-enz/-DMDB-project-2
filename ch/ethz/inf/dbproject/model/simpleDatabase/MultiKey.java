package ch.ethz.inf.dbproject.model.simpleDatabase;

import java.util.*;

public class MultiKey {
  // string representation of an object
  private final List<String> keys; 
  
  public MultiKey(){
	  keys = new ArrayList<String>();
  }

  public int hashcode() {
	  StringBuilder string = new StringBuilder();
	  for (String key:keys){
		  string.append(key + ":=:");
	  }
	  return string.toString().hashCode();
  }
  
  public List<String> getKeys(){
	  return keys;
  }
  
  public boolean equals(Object other) {
	  MultiKey otherkeys = (MultiKey) other;
	  if(keys.size() != otherkeys.getKeys().size()){
		  return false;
	  }
	  for (int i = 0; i < keys.size(); i++){
		  if (!keys.get(i).equals(otherkeys.keys.get(i))){
			  return false;
		  }
	  }
	  return true;
  }
}