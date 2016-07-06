package it.larusba.neo4j.jdbc.threads.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;

public abstract class QueryExecutorThread extends ExecutorThread
{
  protected PreparedStatement stmt;
  
  protected QueryExecutorThread(String databaseUrl) throws SQLException {
    Connection connection = DriverManager.getConnection(databaseUrl);
    this.prepareStatement(connection);
  }
  
  protected void executeQuery()
  {
    try
    {
      stmt.executeQuery();
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  protected abstract void prepareStatement(Connection connection) throws SQLException;
}
