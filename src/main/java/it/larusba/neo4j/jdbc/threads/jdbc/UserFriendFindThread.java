package it.larusba.neo4j.jdbc.threads.jdbc;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserFriendFindThread extends QueryExecutorThread
{
  private final static String QUERY = "MATCH (u:User {id:{1}})-[:FRIEND]-(f)-[:FRIEND]-(fof) WHERE NOT (u)-[:FRIEND]-(fof) RETURN fof.id as fof, count(*) as score ORDER BY score DESC LIMIT 10;";

  public UserFriendFindThread(String databaseUrl) throws SQLException
  {
    super(databaseUrl);
    this.type = ThreadType.USER_FRIEND_READER;
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
      stmt.setInt(1, Utils.getRandomUserId());
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }
}