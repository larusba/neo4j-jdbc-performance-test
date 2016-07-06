package it.larusba.neo4j.jdbc.threads.factory;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;
import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.threads.driver.RelationshipCreateThread;
import it.larusba.neo4j.jdbc.threads.driver.UserCreateThread;
import it.larusba.neo4j.jdbc.threads.driver.UserFindThread;
import it.larusba.neo4j.jdbc.threads.driver.UserFriendFindThread;
import it.larusba.neo4j.jdbc.threads.driver.UserFriendSuggestionThread;

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
