/*
 * Copyright (c) 2016 LARUS Business Automation [http://www.larus-ba.it]
 * <p>
 * This file is part of the "LARUS Neo4j JDBC Performance Test".
 * <p>
 * The "LARUS Neo4j JDBC PErformance Test" is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created on 07/07/2016
 */

package it.larusba.threads.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.larusba.threads.ExecutorThread;

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
