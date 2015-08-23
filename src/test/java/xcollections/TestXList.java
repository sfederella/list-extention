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
	public void testAVerSiAndaElMap() {
		assertEquals("[4, 4, 5]",xlist.map(e -> e.length()).toString());
	}
	
	@Test
	public void testAVerSiAndaElFoldl() {
		assertEquals((Integer)13,xlist.foldl(0,(e1,e2) -> e1+e2.length()));
	}
	
	@Test
	public void testAVerSiAndaElFoldl1() {
		assertEquals((Integer)13,xlist.map(e -> e.length()).foldl1((e1,e2) -> e1+e2));
	}
	
	@Test
	public void testAVerSiAndaElAddMejorado() {
		xlist.add("todo","bien");
		assertEquals("[hola, como, andas, todo, bien]",xlist.toString());
	}
}
