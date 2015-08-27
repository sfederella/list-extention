package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import exceptions.EmptyListException;
import exceptions.NotFlattenizableListException;

public class XList<E> extends ArrayList<E>{

	private static final long serialVersionUID = 624622761768981865L;
	
// How can I instantiate a XList?

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

// And how can I add some elements to a XList?
	
	@SuppressWarnings("unchecked")
	public boolean add(E arg, E ... args){
		boolean result = super.add(arg);
		for(E elem : args){
			result = result && super.add(elem);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addAll(Collection<? extends E> arg, Collection<? extends E> ... args){
		boolean result = super.addAll(arg);
		for(Collection<? extends E> col : args){
			result = result && super.addAll(col);
		}
		return result;
	}
	
	public boolean addIfNotRepeated(E elem){
		if (!contains(elem)) {
			return add(elem);
		} else return false;
	}
	
	public boolean addAllNotRepeated(Collection<? extends E> c){
		boolean result = true;
		for(E elem : c){
			result = result && addIfNotRepeated(elem);
		}
		return result;
	}
	
// Do you still need more? Some utils maybe?
	
	public int timesRepeated(E elem){
		int timesRepeated=0;
		for(E aux : this){
			if(aux==elem) timesRepeated++;
		}
		return timesRepeated;
	}
	
	public void removeRepeated(){
		for(int i=size(); i>0; i--){
			if (timesRepeated(get(i-1))>1) remove(i-1);
		}
	}
	
	public XList<E> withoutRepeted(){
		XList<E> notRepeated = new XList<E>();
		notRepeated.addAllNotRepeated(this);
		return notRepeated;
	}
	
	public Set<E> asSet(){
		Set<E> aSet = new HashSet<E>();
		aSet.addAll(this);
		return aSet;
	}
	
	@SuppressWarnings("unchecked")
	public IntXList asInt(){
		IntXList intlist = new IntXList();
		intlist.addAll((XList<Integer>)this);
		return intlist;
	}
	
	@SuppressWarnings("unchecked")
	public DoubleXList asDouble(){
		DoubleXList doublelist = new DoubleXList();
		doublelist.addAll((XList<Double>)this);
		return doublelist;
	}
	
	@SuppressWarnings("unchecked")
	public FloatXList asFloat(){
		FloatXList floatlist = new FloatXList();
		floatlist.addAll((XList<Float>)this);
		return floatlist;
	}
	
	@SuppressWarnings("unchecked")
	public LongXList asLong(){
		LongXList longlist = new LongXList();
		longlist.addAll((XList<Long>)this);
		return longlist;
	}
	
	public XList<E> inverse(){
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
	
	public XList<E> init(){
		XList<E> init = new XList<E>();
		init.addAll(this);
		init.remove(size()-1);
		return init;
	}
	
	public E last(){
		return get(size()-1);
	}
	
	/**
	 * Une la lista en un String separando los elementos por el "spliptter".
	 * Usa el metodo ".toString()" por cada elemento. 
	 * @author sfederella
	 * @param splitter
	 */
	
	public String toString(String splitter){
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
	
	private <T> XList<T> flatten(XList<? extends Collection<? extends T>> list){
		XList<T> flattened = new XList<T>();
		for(Collection<? extends T> col : list ){
			flattened.addAll(col);
		}
		return flattened;
	}
	
	@SuppressWarnings("unchecked")
	public <T> XList<T> flattenAs(Class<T> cast){
		try{
			XList<T> flattened = flatten((XList<? extends Collection<? extends T>>) this);
			return flattened;
		} catch(Exception e) {
			throw new NotFlattenizableListException("Not possible to flatten the list");
		}
	}

	@SuppressWarnings("unchecked")
	public XList<E> join(Collection<? extends E> ... args){
		XList<E> joined = new XList<E>();
		joined.addAll(this);
		for(Collection<? extends E> list : args){
			joined.addAll(list);
		}
		return joined;
	}
	
	@SuppressWarnings("unchecked")
	public XList<E> union(Collection<? extends E> ... args){
		XList<E> union = new XList<E>();
		union.addAll(this.withoutRepeted());
		for(Collection<? extends E> list : args){
			union.addAllNotRepeated(list);
		}
		return union; 
	}
	
	@SuppressWarnings("unchecked")
	public XList<E> intersection(Collection<? extends E> ... args){
		XList<E> intersection = new XList<E>();
		XList<Collection<? extends E>> auxList = new XList<Collection<? extends E>>();
		auxList.add(this);
		for(Collection<? extends E> arg : args) auxList.add(arg);
		for(Collection<? extends E> col : auxList){
			for(E elem : col){
				if(auxList.all(l-> l.contains(elem))){
					intersection.add(elem);
				}
			}
		}
		return intersection;	
	}
	
	@SuppressWarnings("unchecked")
	public void substract(E ... args){
		for(E elem : args){
			if(this.contains(elem)) this.remove((Object)elem);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void substractFrom(Collection<? extends E> ... args){
			for(Collection<? extends E> col : args){
				for(E elem : col){
					if(this.contains(elem)) this.remove((Object)elem);
				}
			}
	}
	
	@SuppressWarnings("unchecked")
	public XList<E> diference(E ... args){
		XList<E> diference = new XList<E>();
		XList<E> auxList = new XList<E>();
		for(E arg : args) auxList.add(arg);
		for(E elem : this){
			if(!auxList.contains(elem)){
				diference.add(elem);
			}
		}
		return diference;
	}
	
	@SuppressWarnings("unchecked")
	public XList<E> diferenceFrom(Collection<? extends E> ... args){
		XList<E> diference = new XList<E>();
		XList<Collection<? extends E>> auxList = new XList<Collection<? extends E>>();
		for(Collection<? extends E> arg : args) auxList.add(arg);
		for(E elem : this){
			if(auxList.all(col->!col.contains(elem))){
				diference.add(elem);
			}
		}
		return diference;
	}
	
// And finally some superior order methods!

	public boolean all(Predicate<? super E> predicate){
		for(E elem : this){
			if(!predicate.test(elem)){
				return false;
			}
		}
		return true;
	}
	
	public boolean any(Predicate<? super E> predicate){
		for(E elem : this){
			if(predicate.test(elem)){
				return true;
			}
		}
		return false;
	}
	
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
		return inverse().foldl(seed, reducer);
	}
	
	public E foldl1(BiFunction<E,E,E> reducer){
		try {
		E result = this.remove(0); 
		for (E elem : this)
			result = reducer.apply(result, elem);
		return result;
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyListException("Not posible to foldl1 an empty list");
		}
	}
	
	public XList<E> sortedBy(Comparator<? super E> c){
		XList<E> sorted = new XList<E>();
		sorted.addAll(this);
		sorted.sort(c);
		return sorted;
	}
	
	public E maxBy(Comparator<? super E> c){
		E max = get(0);
		for(E elem : this){
			if(c.compare(elem,max)==1){
				max=elem;
			}
		}
		return max;
	}
	
	public E minBy(Comparator<? super E> c){
		E min = get(0);
		for(E elem : this){
			if(c.compare(elem,min)==-1){
				min=elem;
			}
		}
		return min;
	}
	
	/*
	 * TODO
	 * 
	 *  v2.0:
	 *  *parser de strings
	 *  *ER
	 */
}
