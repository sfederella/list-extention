package xcollections;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestXList {
	XList<String> xlist;
	
	@Before
	public void setUp() {
		xlist = XList.of("hola","como","andas");
	}
	
	@Test
	public void testAVerSiAndaElGetInverse() {
		assertEquals("[andas, como, hola]",xlist.getInverse().toString());
	}
	
	@Test
	public void testAVerSiAndaElMap() {
		assertEquals("[4, 4, 5]",xlist.map(e -> e.length()).toString());
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
	
	@Test
	public void testAVerSiAndaElAddMejorado() {
		xlist.add("todo","bien");
		assertEquals("[hola, como, andas, todo, bien]",xlist.toString());
	}
	
	@Test
	public void testAVerSiAndaElSumConInt() {
		assertEquals((Integer)13,xlist.map(e -> e.length()).sum());
	}
	
	@Test
	public void testAVerSiAndaElSumConDouble() {
		assertEquals((Double)7.0,XList.of(1.0,2.0,4.0).sum());
	}
	
	@Test
	public void testAVerSiAndaElSumConListaVacia() {
		assertEquals(null,XList.of().sum());
	}
	
	@Test
	public void testAVerSiAndaElSumConOtro() {
		assertEquals(null,xlist.sum());
	}
	
	@Test
	public void testAVerComoFuncionaElJoin() {
		assertEquals("hola -> como -> andas",xlist.join(" -> "));
		assertEquals("hola, como, andas",xlist.join(", "));
		assertEquals("1+2+3",XList.of(1,2,3).join("+"));
	}
	
}
