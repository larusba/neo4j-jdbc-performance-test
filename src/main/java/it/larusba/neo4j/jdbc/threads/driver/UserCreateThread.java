package it.larusba.neo4j.jdbc.threads.driver;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.SQLException;

import org.neo4j.driver.v1.AuthToken;

public class UserCreateThread extends QueryExecutorThread
{
  private final static String QUERY = "MERGE (u:User {id:{1}}) ON CREATE SET u.name = {2}, u.age={3}";

  public UserCreateThread(String databaseUrl, AuthToken auth) throws SQLException
  {
    super(databaseUrl, auth);
    this.type = ThreadType.USER_WRITER;
  }

  @Override
  protected void updateStatement()
  {
    parameters.put("1", Utils.getNextUserId());
    parameters.put("2", Utils.getRandomName());
    parameters.put("3", Utils.getRandomAge());
  }

  @Override
  protected String getQuery()
  {
    return QUERY;
  }
}