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

import java.sql.SQLException;
import java.util.List;

public abstract class ExecutorThreadFactory
{
  public abstract List<ExecutorThread> createThreads(ThreadType type, int threadsNumber, String databaseUrl) throws SQLException;

  public abstract ExecutorThread getThreadOfType(ThreadType type, String databaseUrl) throws SQLException;

  public static int getSpecificTypeNumber(ThreadType type, int total)
  {
    int typeNumber = 0;

    switch (type)
    {
      case USER_WRITER:
        typeNumber = (int) (total * 0.1 * 0.2);
        break;
      case RELATIONSHIP_WRITER:
        typeNumber = (int) (total * 0.1 * 0.8);
        break;
      case USER_READER:
        typeNumber = (int) (total * 0.9 * 0.2);
        break;
      case USER_FRIEND_READER:
        typeNumber = (int) (total * 0.9 * 0.7);
        break;
      case USER_FRIEND_SUGGESTION_READER:
        typeNumber = (int) (total * 0.9 * 0.1);
        break;
    }

    return typeNumber;
  }
}
