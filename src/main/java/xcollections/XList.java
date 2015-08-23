package xcollections;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class XList<E> extends ArrayList<E>{
	
	private static final long serialVersionUID = 624622761768981865L;
	
	
// Aca pongo como se instancian las XList.

	/**
	 * Para que sea mas comodo crear listas directamente con elementos dentro.
	 * Se usa:	XList<Tipo> xlist = new XList<Tipo>(E1,E2,...,En);
	 */
	
	@SafeVarargs
	public XList(E ... args){
		super();
		for(E elem : args){
			add(elem);
		}
	}

	/**
	 * Para que sea aun mas comodo crear listas directamente con elementos dentro.
	 * Se usa:	XList<Tipo> xlist = XList.of(E1,E2,...,En);
	 */
	
	@SafeVarargs
	public static <T>  XList<T> of(T ... args){
		return new XList<T>(args);
	}
	
	
// Aca pongo algunos utils.
	
	@SuppressWarnings("unchecked")
	public boolean add(E arg0, E ... args){
		add(arg0);
		for(E elem : args){
			add(elem);
		}
		return true;
	}
	
	public XList<E> getInverse(){
		XList<E> inverse = new XList<E>();
		for(int i=this.size(); i>0; i--){
			inverse.add(get(i));
		}
		return inverse;
	}
	
	public E head(){
		return get(0);
	}
	
	public XList<E> tail(){
		XList<E> tail = new XList<E>();
		tail.addAll(this);
		tail.remove(0);
		return tail;
	}
	
	public E last(){
		return get(size()-1);
	}
	
// Aca pongo algunas funciones de orden superior.
	
	public <T> XList<T> map(Function<? super E, ? extends T> mapper){
		XList<T> mappedList = new XList<T>();
		for(E elem : this){
			T mappedElem = mapper.apply(elem);
			mappedList.add(mappedElem);
		}
		return mappedList;
	}
	
	public XList<E> filter(Predicate<? super E> predicate){
		XList<E> filteredList = new XList<E>();
		for(E elem : this){
			if(predicate.test(elem)){
				filteredList.add(elem);
			}
		}
		return filteredList;
	}
	
	public <T> T foldl(T seed, BiFunction<T, ? super E, T> reducer){
		T result = seed; 
		for (E elem : this)
		     result = reducer.apply(result, elem);
		 return result;   
	}
	
	public <T> T foldr(T seed, BiFunction<T, ? super E, T> reducer){
		return getInverse().foldl(seed, reducer);
	}
	
	public E foldl1(BiFunction<E,E,E> reducer){
		E result = this.remove(0); 
		for (E elem : this)
			result = reducer.apply(result, elem);
		return result;   
	}
	
}