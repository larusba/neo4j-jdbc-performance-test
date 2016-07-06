package it.larusba.neo4j.jdbc.threads.driver;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.SQLException;

import org.neo4j.driver.v1.AuthToken;

public class UserFriendFindThread extends QueryExecutorThread
{
  private final static String QUERY = "MATCH (u:User {id:{1}})-[:FRIEND]-(f)-[:FRIEND]-(fof) WHERE NOT (u)-[:FRIEND]-(fof) RETURN fof.id as fof, count(*) as score ORDER BY score DESC LIMIT 10;";

  public UserFriendFindThread(String databaseUrl, AuthToken auth) throws SQLException
  {
    super(databaseUrl, auth);
    this.type = ThreadType.USER_FRIEND_READER;
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