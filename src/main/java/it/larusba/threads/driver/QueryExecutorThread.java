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

package it.larusba.threads.driver;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.AuthToken;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import it.larusba.threads.ExecutorThread;

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
}
