package it.larusba.neo4j.jdbc.threads.factory;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;
import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.threads.jdbc.RelationshipCreateThread;
import it.larusba.neo4j.jdbc.threads.jdbc.UserCreateThread;
import it.larusba.neo4j.jdbc.threads.jdbc.UserFindThread;
import it.larusba.neo4j.jdbc.threads.jdbc.UserFriendFindThread;
import it.larusba.neo4j.jdbc.threads.jdbc.UserFriendSuggestionThread;

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
