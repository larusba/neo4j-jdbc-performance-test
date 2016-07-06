package it.larusba.neo4j.jdbc.threads.driver;

import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.SQLException;
import java.util.Map;

import org.neo4j.driver.v1.AuthToken;

public class RelationshipCreateThread extends QueryExecutorThread
{
  private final static String         QUERY      = "MATCH (u:User {id:{1}}), (f:User {id:{2}}) MERGE (u)-[:FRIEND]-(f);";

  public RelationshipCreateThread(String databaseUrl, AuthToken auth) throws SQLException
  {
    super(databaseUrl, auth);
    this.type = ThreadType.RELATIONSHIP_WRITER;
  }

  @Override
  protected void updateStatement()
  {
    int a = Utils.getRandomUserId();
    int b = Utils.getRandomUserId();
    b = (b == a ? Utils.getRandomUserId() : b);
    parameters.clear();
    parameters.put("1", a);
    parameters.put("2", b);
  }
  
  public String getQuery(){
    return QUERY;
  }
  
  public Map<String, Object> getParameters(){
    return this.parameters;
  }
}