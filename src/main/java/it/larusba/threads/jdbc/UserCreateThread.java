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

import it.larusba.threads.ThreadType;
import it.larusba.utils.Utils;

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
  
  @Override
  public String getQuery()
  {
    return QUERY;
  }
}
