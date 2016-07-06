package it.larusba.neo4j.jdbc.threads.driver;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.SQLException;

import org.neo4j.driver.v1.AuthToken;

public class UserFindThread extends QueryExecutorThread
{
  private final static String QUERY = "MATCH (u:User {id:{1}}) RETURN u;";

  public UserFindThread(String databaseUrl, AuthToken auth) throws SQLException
  {
    super(databaseUrl, auth);
    this.type = ThreadType.USER_READER;
  }

  @Override
  protected void updateStatement()
  {
    parameters.put("1", Utils.getRandomUserId());
  }

  @Override
  protected String getQuery()
  {
    return QUERY;
  }
}