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
import it.larusba.threads.jdbc.RelationshipCreateThread;
import it.larusba.threads.jdbc.UserCreateThread;
import it.larusba.threads.jdbc.UserFindThread;
import it.larusba.threads.jdbc.UserFriendFindThread;
import it.larusba.threads.jdbc.UserFriendSuggestionThread;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class QueryExecutorThreadFactoryJDBC extends ExecutorThreadFactory
{
  public List<ExecutorThread> createThreads(ThreadType type, int threadsNumber, String databaseUrl) throws SQLException
  {
    List<ExecutorThread> list = new LinkedList<ExecutorThread>();

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
        thread = new UserCreateThread(databaseUrl);
        break;
      case RELATIONSHIP_WRITER:
        thread = new RelationshipCreateThread(databaseUrl);
        break;
      case USER_READER:
        thread = new UserFindThread(databaseUrl);
        break;
      case USER_FRIEND_READER:
        thread = new UserFriendFindThread(databaseUrl);
        break;
      case USER_FRIEND_SUGGESTION_READER:
        thread = new UserFriendSuggestionThread(databaseUrl);
        break;
    }

    return thread;
  }
}
