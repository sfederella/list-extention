package xcollections;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;

import core.IntXList;
import core.XList;
import exceptions.EmptyListException;

@SuppressWarnings("unused")
public class TestXList {
	XList<String> xlist;

	@Before
	public void setUp() {
		xlist = XList.of("hola","como","andas");
	}
	
	@Test
	public void testAVerSiAndaElGetInverse() {
		assertEquals(XList.of("andas", "como","hola"),xlist.inverse());
	}
	
	@Test
	public void testAVerSiAndaElMap() {
		assertEquals(XList.of(4,4,5),xlist.map(e -> e.length()));
	}
	
	@Test
	public void testAVerSiAndaElFoldl() {
		assertEquals((Integer)13,xlist.foldl(0,(e1,e2) -> e1+e2.length()));
	}
	
	@Test
	public void testAVerSiAndaElFoldr() {
		assertEquals((Integer)(-13),xlist.foldr(0,(e1,e2) -> e1-e2.length()));
	}
	
	@Test
	public void testAVerSiAndaElFoldl1() {
		assertEquals((Integer)6,XList.of(1,2,3).foldl1((e1,e2) -> e1+e2));
	}
	
	@Test (expected = EmptyListException.class)
	public void testAVerSiAndaElFoldl1ListaVacia() {
		XList.<Integer>of().foldl1((e1,e2) -> e1+e2);
	}
	
	@Test
	public void testAVerSiAndaElAddMejorado() {
		xlist.add("todo","bien");
		assertEquals(XList.of("hola","como","andas","todo","bien"),xlist);
	}
	
	@Test
	public void testAVerSiAndaElSumConInt() {
		assertEquals((Integer)13,xlist.map(e -> e.length()).asInt().sum());
	}
	
	@Test
	public void testAVerSiAndaElSumConDouble() {
		assertEquals((Double)7.0,XList.of(1.0,2.0,4.0).asDouble().sum());
	}
	
	@Test (expected = RuntimeException.class)
	public void testAVerSiAndaLaException() {
		XList.of(1.0,2.0,4.0).asInt().sum();
	}
	
	@Test
	public void testAVerSiAndaElSumConListaVacia() {
		assertEquals((Integer)0, XList.of().asInt().sum());
	}
	
	@Test
	public void testAVerSiAndaElSumConListaVaciaDouble() {
		assertEquals((Double)0.0, XList.of().asDouble().sum());
	}
	
	@Test
	public void testAVerComoFuncionaElJoin() {
		assertEquals("hola -> como -> andas",xlist.toString(" -> "));
		assertEquals("hola, como, andas",xlist.toString(", "));
		assertEquals("1+2+3",XList.of(1,2,3).toString("+"));
	}
	
	@Test
	public void testAVerSiAndaElremoveRepeated() {
		XList<Integer> auxList = XList.of(1,2,3,1);
		auxList.removeRepeated();
		assertEquals(XList.of(1,2,3),auxList);
	}
	
	@Test
	public void testAVerSiAndaElFlatten() {
		assertEquals((Integer)12,XList.of(XList.of(1,2,3),XList.of(2,4)).flattenAs(Integer.class).asInt().sum());
		assertEquals(xlist,xlist.map((s)-> XList.of(s)).flattenAs(String.class));
	}
	
	@Test (expected = EmptyListException.class)
	public void testAVerSiAndaAverage() {
		XList.of().asInt().average();
	}

	@Test
	public void testAVerSiAndaElAveregeDouble() {
		assertEquals((Double)2.0,XList.of(1.0,2.0,3.0).asDouble().average());
	}
	
}
