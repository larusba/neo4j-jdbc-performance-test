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

package it.larusba.threads.factory;

import it.larusba.threads.ExecutorThread;
import it.larusba.threads.ThreadType;
import it.larusba.threads.driver.RelationshipCreateThread;
import it.larusba.threads.driver.UserCreateThread;
import it.larusba.threads.driver.UserFindThread;
import it.larusba.threads.driver.UserFriendFindThread;
import it.larusba.threads.driver.UserFriendSuggestionThread;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.neo4j.driver.v1.AuthToken;
import org.neo4j.driver.v1.AuthTokens;

public class QueryExecutorThreadFactoryDriver extends ExecutorThreadFactory
{
  AuthToken auth = null;

  public List<ExecutorThread> createThreads(ThreadType type, int threadsNumber, String databaseUrl) throws SQLException
  {
    List<ExecutorThread> list = new LinkedList<ExecutorThread>();

    if (Objects.isNull(auth))
    {
      auth = getAuth(databaseUrl);
    }

    for (int i = threadsNumber; i > 0; i--)
    {
      list.add(getThreadOfType(type, databaseUrl));
    }

    return list;
  }

  public ExecutorThread getThreadOfType(ThreadType type, String databaseUrl) throws SQLException
  {
    ExecutorThread thread = null;

    switch (type)
    {
      case USER_WRITER:
        thread = new UserCreateThread(databaseUrl, this.auth);
        break;
      case RELATIONSHIP_WRITER:
        thread = new RelationshipCreateThread(databaseUrl, this.auth);
        break;
      case USER_READER:
        thread = new UserFindThread(databaseUrl, this.auth);
        break;
      case USER_FRIEND_READER:
        thread = new UserFriendFindThread(databaseUrl, this.auth);
        break;
      case USER_FRIEND_SUGGESTION_READER:
        thread = new UserFriendSuggestionThread(databaseUrl, this.auth);
        break;
    }

    return thread;
  }

  private AuthToken getAuth(String databaseUrl)
  {
    AuthToken auth = AuthTokens.none();

    String[][] params = this.getUrlParams(databaseUrl);
    if (params.length > 1)
    {
      String username = "", password = "";
      for (String[] param : params)
      {
        if ("user".equals(param[0]))
        {
          username = param[1];
        }
        else if ("password".equals(param[0]))
        {
          password = param[1];
        }
      }
      if (!username.isEmpty() && !password.isEmpty())
      {
        auth = AuthTokens.basic(username, password);
      }
    }
    
    return auth;
  }
  
  private String[][] getUrlParams(String databaseUrl)
  {
    String[][] urlParams;

    String subUrl = databaseUrl.substring(databaseUrl.indexOf("?") + 1, databaseUrl.length());

    String[] parameters = subUrl.split(",");
    urlParams = new String[parameters.length][];

    int i = 0;
    for (String parameter : parameters)
    {
      urlParams[i++] = parameter.split("=");
    }

    return urlParams;
  }
}
