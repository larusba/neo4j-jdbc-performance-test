package it.larusba.neo4j.jdbc.threads.jdbc;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserFriendSuggestionThread extends QueryExecutorThread
{
  private final static String QUERY = "MATCH (u:User {id:{1}})-[:FRIEND]-(f) RETURN f.id;";

  public UserFriendSuggestionThread(String databaseUrl) throws SQLException
  {
    super(databaseUrl);
    this.type = ThreadType.USER_FRIEND_SUGGESTION_READER;
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