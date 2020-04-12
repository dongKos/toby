package toby.user.dao;

import java.sql.Connection;

public interface ConnectionMaker {

	Connection makeConnection() throws Exception;
}
