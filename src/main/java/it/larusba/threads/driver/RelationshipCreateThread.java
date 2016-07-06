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

import it.larusba.threads.ThreadType;
import it.larusba.utils.Utils;

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
