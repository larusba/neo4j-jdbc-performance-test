package it.larusba.neo4j.jdbc.threads.driver;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.AuthToken;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;

public abstract class QueryExecutorThread extends ExecutorThread
{
  protected Session                   session;

  protected final Map<String, Object> parameters = new HashMap<String, Object>();

  protected QueryExecutorThread(String databaseUrl, AuthToken auth) throws SQLException
  {
    Driver driver = GraphDatabase.driver(databaseUrl.replace("jdbc:neo4j:", ""), auth);
    session = driver.session();
    this.updateStatement();
  }

  protected void executeQuery()
  {
    StatementResult result = session.run(getQuery(), parameters);
    result.consume();
  }

  protected abstract String getQuery();
}
