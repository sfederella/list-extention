package xcollections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class XList<E> extends ArrayList<E>{
	
	private Class<?> parameterClass;
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
	
	public boolean add(E elem){
		if(this.isEmpty()) setType(elem);
		return super.add(elem);
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(E arg, E ... args){
		if(this.isEmpty()) setType(arg);
		boolean result = super.add(arg);
		for(E elem : args){
			result = result && super.add(elem);
		}
		return result;
	}
	
	private void setType(E elem){
		parameterClass = elem.getClass(); 
	}
	
	public Class<?> getType(){
		return parameterClass;
	}

// Aca pongo algunos utils.
	
	public XList<E> getInverse(){
		XList<E> inverse = new XList<E>();
		for(int i=size(); i>0; i--){
			inverse.add(get(i-1));
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
	
	/**
	 * Si los elementos de la lista son numeros retorna su suma, caso contrario retorna null.
	 * @author sfederella
	 */
	
	@SuppressWarnings("unchecked")
	public E sum(){
		if(parameterClass==Integer.class) {
			return (E) foldl(0,(e1,e2) -> (Integer)e1 + (Integer)e2);
		} else if(parameterClass==Double.class) {
			return (E) foldl(0.0,(e1,e2) -> (Double)e1 + (Double)e2);
		} else {
			return null; //XXX CHECK
		}
	}
	
	/**
	 * Une la lista en un String separando los elementos por el "spliptter".
	 * Usa el metodo ".toString()" por cada elemento. 
	 * @author sfederella
	 * @param splitter
	 */
	
	public String join(String splitter){
		String joined = "";
		E firstElem = get(0);
		for(E elem : this){
			if(elem!=firstElem) {
				joined = joined.concat(splitter);
			}
			joined = joined.concat(elem.toString());
		}
		return joined;
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
	
	public <T> XList<T> flatMap(Function<? super E, ? extends List<? extends T>> mapper){
		XList<T> mappedList = new XList<T>();
		for(E elem : this){
			List<? extends T> auxList = mapper.apply(elem);
			mappedList.addAll(auxList);
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
