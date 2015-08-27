package core; 

import exceptions.EmptyListException;

public class DoubleXList extends XList<Double>{

	private static final long serialVersionUID = 959124460135814625L;
	
	@SafeVarargs
	public DoubleXList(Double ... args){
		super(args);
	}
	
	public Double sum(){
		return foldl(0.0,(e1,e2)->e1+e2);
	}
	
	public Double average(){
		if(isEmpty()) {
			throw new EmptyListException("Can't get the average of an empty list");
		} else return (double)sum()/size();
	}

}
