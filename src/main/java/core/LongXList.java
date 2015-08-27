package core;

import exceptions.EmptyListException;

public class LongXList extends XList<Long> {

	private static final long serialVersionUID = 2409532142576352457L;

	@SafeVarargs
	public LongXList(Long ... args){
		super(args);
	}
	
	public Long sum(){
		return foldl(0L,(e1,e2)->e1+e2);
	}
	
	public Double average(){
		if(isEmpty()) {
			throw new EmptyListException("Can't get the average of an empty list");
		} else return (double)sum()/size();
	}
	
}
