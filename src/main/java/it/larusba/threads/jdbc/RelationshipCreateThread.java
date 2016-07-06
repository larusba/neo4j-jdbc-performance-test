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
