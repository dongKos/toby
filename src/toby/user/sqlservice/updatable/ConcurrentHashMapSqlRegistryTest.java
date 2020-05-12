package toby.user.sqlservice.updatable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import toby.user.sqlservice.SqlNotFoundException;
import toby.user.sqlservice.SqlUpdateFailureException;
import toby.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}
	
//	UpdatableSqlRegistry sqlRegistry;
//	
//	@Before
//	public void setup() {
//		sqlRegistry = new ConcurrentHashMapSqlRegistry();
//		
//		sqlRegistry.registerSql("KEY1", "SQL1");
//		sqlRegistry.registerSql("KEY2", "SQL2");
//		sqlRegistry.registerSql("KEY3", "SQL3");
//	}
//	
//	@Test
//	public void find() throws Exception {
//		checkFindResult("SQL1", "SQL2", "SQL3");
//	}
//
//	private void checkFindResult(String expected1, String expected2, String expected3) {
//		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
//		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
//		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
//	}
//	
//	@Test(expected=SqlNotFoundException.class)
//	public void unknownKey() throws SqlNotFoundException {
//		sqlRegistry.findSql("SQL 999912391293");
//	}
//	
//	@Test
//	public void updateSingle() throws Exception {
//		sqlRegistry.updateSql("KEY2", "Modified2");
//		checkFindResult("SQL1", "Modified2", "SQL3");
//	}
//	
//	@Test
//	public void updateMulti() throws Exception {
//		Map<String, String> sqlmap = new HashMap<String, String>();
//		
//		sqlmap.put("KEY1", "Modified1");
//		sqlmap.put("KEY3", "Modified3");
//		
//		sqlRegistry.updateSql(sqlmap);
//		checkFindResult("Modified1", "SQL2", "Modified3");
//	}
//	
//	@Test(expected=SqlUpdateFailureException.class)
//	public void updateWithNotExistngKey() throws Exception {
//		sqlRegistry.updateSql("SQL19287398127", "Modified2");
//	}
//	
//	

}
