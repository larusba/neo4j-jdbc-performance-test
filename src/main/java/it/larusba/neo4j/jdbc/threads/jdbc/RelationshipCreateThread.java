package it.larusba.neo4j.jdbc.threads.jdbc;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class RelationshipCreateThread extends QueryExecutorThread
{
  private final static String QUERY = "MATCH (u:User {id:{1}}), (f:User {id:{2}}) MERGE (u)-[:FRIEND]-(f);";

  public RelationshipCreateThread(String databaseUrl) throws SQLException
  {
    super(databaseUrl);
    this.type = ThreadType.RELATIONSHIP_WRITER;
  }

  protected void prepareStatement(Connection connection) throws SQLException
  {
    this.stmt = connection.prepareStatement(QUERY);
    this.updateStatement();
  }

  @Override
  protected void updateStatement()
  {
    int a = Utils.getRandomUserId();
    int b = Utils.getRandomUserId();
    b = (b == a ? Utils.getRandomUserId() : b);
    try
    {
      stmt.setInt(1, Utils.getRandomUserId());
      stmt.setInt(2, Utils.getRandomUserId());
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }
}