package it.larusba.neo4j.jdbc.threads.factory;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;
import it.larusba.neo4j.jdbc.threads.ThreadType;

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
