package xmlTest.dao;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/junit.xml")
public class JUnitTest {
	
	@Autowired
	private ApplicationContext context;
	
	//static JUnitTest testObject;
	static Set<JUnitTest> testObjSet = new HashSet<JUnitTest> ();
	static ApplicationContext contextObject = null;
	
	@Test
	public void test1() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjSet, not(hasItem(this)));
		testObjSet.add(this);
		
		assertThat(contextObject == null || 
				contextObject == this.context, is(true));
		contextObject = this.context;
	}
	
	@Test
	public void test2() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjSet, not(hasItem(this)));
		testObjSet.add(this);
		
		assertThat(contextObject == null ||  
				contextObject == this.context, is(true));
		contextObject = this.context;
	}
	
	@Test 
	public void test3() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjSet, not(hasItem(this)));
		testObjSet.add(this);
		
		assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
		contextObject = this.context;
	}
}
