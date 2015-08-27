package core;

import exceptions.EmptyListException;

public class IntXList extends XList<Integer> {

	private static final long serialVersionUID = -6739881722729109919L;
	
	@SafeVarargs
	public IntXList(Integer ... args){
		super(args);
	}
	
	public Integer sum(){
		return foldl(0,(e1,e2)->e1+e2);
	}

	public Double average(){
		if(isEmpty()) {
			throw new EmptyListException("Can't get the average of an empty list");
		} else return (double)sum()/size();
	}

}
