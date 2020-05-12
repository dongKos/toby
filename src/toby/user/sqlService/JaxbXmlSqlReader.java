package toby.user.sqlservice;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import toby.user.dao.UserDao;
import toby.user.sqlservice.jaxb.SqlType;
import toby.user.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader{
	private String sqlmapFile = DEFALUT_SQLMAP_FILE;

	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}
	
	private static final String DEFALUT_SQLMAP_FILE = "sqlmap.xml";

	@Override
	public void read(SqlRegistry sqlRegistry) {
		String contextPath = Sqlmap.class.getPackage().getName();

		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			InputStream is = UserDao.class.getResourceAsStream(this.sqlmapFile);

			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

			for (SqlType sql : sqlmap.getSql()) {
//				sqlMap.put(sql.getKey(), sql.getValue());
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}

		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
