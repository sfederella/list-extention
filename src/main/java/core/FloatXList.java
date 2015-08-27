package core;

import exceptions.EmptyListException;

public class FloatXList extends XList<Float>{

	private static final long serialVersionUID = -3347984970943725585L;

	@SafeVarargs
	public FloatXList(Float ... args){
		super(args);
	}
	
	public Float sum(){
		return foldl(0F,(e1,e2)->e1+e2);
	}
	
	public Double average(){
		if(isEmpty()) {
			throw new EmptyListException("Can't get the average of an empty list");
		} else return (double)sum()/size();
	}
	
}
