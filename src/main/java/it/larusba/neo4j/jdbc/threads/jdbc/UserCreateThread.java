package it.larusba.neo4j.jdbc.threads.jdbc;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserCreateThread extends QueryExecutorThread
{
  private final static String QUERY = "MERGE (u:User {id:{1}}) ON CREATE SET u.name = {2}, u.age={3}";

  public UserCreateThread(String databaseUrl) throws SQLException
  {
    super(databaseUrl);
    this.type = ThreadType.USER_WRITER;
  }

  @Override
  protected void prepareStatement(Connection connection) throws SQLException
  {
    this.stmt = connection.prepareStatement(QUERY);
    this.updateStatement();
  }

  @Override
  protected void updateStatement()
  {
    try
    {
      stmt.setInt(1, Utils.getNextUserId());
      stmt.setString(2, Utils.getRandomName());
      stmt.setInt(3, Utils.getRandomAge());
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }
}