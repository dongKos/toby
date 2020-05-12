package toby.user.sqlservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import toby.user.dao.UserDao;
import toby.user.sqlservice.jaxb.SqlType;
import toby.user.sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {
	
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	
	private final BaseSqlService baseSqlService = new BaseSqlService();
	
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}
//	public void setSqlmapFile(String sqlmapFile) {
//		this.oxmSqlReader.setSqlmapFile(sqlmapFile);
//	}
	
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	@PostConstruct
	public void loadSql() {
//		this.oxmSqlReader.read(this.sqlRegistry);
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);
		
		this.baseSqlService.loadSql();
	}
	
	private class OxmSqlReader implements SqlReader {
		private Unmarshaller unmarshaller;
//		private static final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
//		private String sqlmapFile = DEFAULT_SQLMAP_FILE;
		
		private Resource sqlmap = new ClassPathResource("/sqlmap.xml");
		
		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}
		
		public void setSqlmap(Resource sqlmap) {
			this.sqlmap = sqlmap;
		}
		
//		public void setSqlmapFile(String sqlmapFile) {
//			this.sqlmapFile = sqlmapFile;
//		}
		
		@Override
		public void read(SqlRegistry sqlRegistry) {
			try {
				Source source = new StreamSource(sqlmap.getInputStream());
				
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
				
				for (SqlType sql : sqlmap.getSql()) {
//					sqlMap.put(sql.getKey(), sql.getValue());
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
				
			} catch (IOException e) {
				throw new IllegalArgumentException(sqlmap.getFilename() + "이름이 이상한거 아뇨?");
			}
			
		}
	}
	
	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
//		try {
//			return this.sqlRegistry.findSql(key);
//		} catch(SqlNotFoundException e) {
//			throw new SqlRetrievalFailureException(e);
//		}
		return this.baseSqlService.getSql(key);
	}

}
