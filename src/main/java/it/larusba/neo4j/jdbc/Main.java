package it.larusba.neo4j.jdbc;

import it.larusba.neo4j.jdbc.threads.ExecutorThread;
import it.larusba.neo4j.jdbc.threads.ThreadType;
import it.larusba.neo4j.jdbc.threads.factory.ExecutorThreadFactory;
import it.larusba.neo4j.jdbc.threads.factory.QueryExecutorThreadFactoryDriver;
import it.larusba.neo4j.jdbc.threads.factory.QueryExecutorThreadFactoryJDBC;
import it.larusba.neo4j.jdbc.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
  // STATIC FIELDS

  private static String URL_PREFIX             = "jdbc:neo4j:";

  private static String URL_BOLT_PREFIX        = "bolt://";

  private static String URL_HTTP_PREFIX        = "http://";

  // DEFAULT VALUES

  private static String DEFAULT_DATABASE_URL   = "localhost";

  private static int    DEFAULT_THREADS_NUMBER = 100;

  private static int    DEFAULT_TIME           = 10;

  public static void main(String[] args) throws SQLException, InterruptedException
  {
    // Management of input arguments
    Properties properties = getProperties(args);

    String databaseUrl = getDatabaseUrl(properties);
    boolean driver = shouldUseDriver(properties);
    checkDatabaseStatus(properties, databaseUrl);

    int executionTime = getExecutionTime(properties);
    int threadsNumber = getThreadsNumber(properties);

    ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
    List<List<ExecutorThread>> threadsList = new LinkedList<List<ExecutorThread>>();

    System.out.println("=========== NEO4J JDBC PERFORMANCE TEST ===========");
    
    System.out.println("Configuration: " + (driver ? "DRIVER" : "JDBC") + " " + (databaseUrl.contains("http") ? "HTTP" : "BOLT"));
    
    // Initialization of threads
    // Threads distribution: 10% writers (20% user, 80% relationships), 90%
    // readers(20% user, 70% friend, 10% suggestion)

    ExecutorThreadFactory factory = shouldUseDriver(properties) ? new QueryExecutorThreadFactoryDriver() : new QueryExecutorThreadFactoryJDBC();

    for (ThreadType type : ThreadType.values())
    {
      int threadsN = QueryExecutorThreadFactoryJDBC.getSpecificTypeNumber(type, threadsNumber);
      System.out.print("Creating " + threadsN + " " + type + " threads...");
      threadsList.add(factory.createThreads(type, threadsN, databaseUrl));
      System.out.println(" DONE");
    }

    System.out.println("Setting up performance test...");

    threadsList.forEach(threadType -> threadType.forEach(thread -> executor.execute(thread)));

    // Starting program execution
    System.out.println("Performance test running...");
    long t = System.currentTimeMillis();
    Utils.start();

    Thread.sleep(executionTime * 1000);

    // Stop all threads
    executor.shutdown();
    threadsList.forEach(threadType -> threadType.forEach(thread -> thread.stop()));
    while (!executor.isTerminated())
    {
    }
    t = System.currentTimeMillis() - t;

    System.out.println("=============== PROCESSING RESULTS ================");

    // Process total results
    long threadsEffectiveNumber = threadsList.stream().mapToLong(threadTypes -> threadTypes.stream().count()).sum();
    int queriesExecuted = threadsList.stream().mapToInt(threadTypes -> threadTypes.stream().mapToInt(ExecutorThread::getExecutedQueries).sum()).sum();

    System.out.println("--------------------- TOTALS ----------------------");
    System.out.println("Number of threads: " + threadsEffectiveNumber);
    System.out.println("Queries executed: " + queriesExecuted);
    System.out.println("Execution time: " + t + "ms");

    for (List<ExecutorThread> typeList : threadsList)
    {
      ThreadType type = typeList.get(0).getType();
      int threadsN = typeList.size();
      int queries = typeList.stream().mapToInt(ExecutorThread::getExecutedQueries).sum();
      long avg = Math.round(typeList.stream().mapToDouble(ExecutorThread::getAverageQueryTime).average().getAsDouble());
      System.out.println("-------------- Details for thread " + type.name() + ":");
      System.out.println("Number of threads: " + threadsN);
      System.out.println("Queries executed: " + queries);
      System.out.println("Average time per query: " + avg + "ms");
    }

    System.out.println("===================================================");
  }

  private static boolean shouldUseDriver(Properties properties)
  {
    return properties.containsKey("--protocol") && "DRIVER".equals(properties.getProperty("--protocol"));
  }

  private static String getDatabaseUrl(Properties properties)
  {
    String databaseUrl = URL_PREFIX;

    if (properties.containsKey("--protocol"))
    {
      String protocol = properties.getProperty("--protocol");
      if ("BOLT".equals(protocol))
      {
        databaseUrl += URL_BOLT_PREFIX;
      }
      else if ("HTTP".equals(protocol))
      {
        databaseUrl += URL_HTTP_PREFIX;
      }
      else if ("DRIVER".equals(protocol))
      {
        databaseUrl += URL_BOLT_PREFIX;
      }
      else
      {
        databaseUrl += URL_BOLT_PREFIX;
      }
    }
    else
    {
      System.out.println("WARNING: No module specified, BOLT will be used (use -p <BOLT/HTTP/DRIVER> to specify protocol)");
      databaseUrl += URL_BOLT_PREFIX;
    }

    if (properties.containsKey("--db-url"))
    {
      databaseUrl += properties.getProperty("--db-url");
    }
    else
    {
      System.out.println("WARNING: No database url specified, localhost will be used (use -d <URL> to specify database url)");
      databaseUrl += DEFAULT_DATABASE_URL;
    }

    return databaseUrl;
  }

  private static int getThreadsNumber(Properties properties)
  {
    int threadsNumber = DEFAULT_THREADS_NUMBER;

    if (properties.containsKey("--threads"))
    {
      threadsNumber = Integer.parseInt(properties.getProperty("--threads"));
    }
    else
    {
      System.out.println("WARNING: No threads number specified, 100 will be used (use -n <n> to specify threads number)");
    }

    return threadsNumber;
  }

  private static int getExecutionTime(Properties properties)
  {
    int executionTime = DEFAULT_TIME;

    if (properties.containsKey("--time"))
    {
      executionTime = Integer.parseInt(properties.getProperty("--time"));
    }
    else
    {
      System.out.println("WARNING: No execution time specified, 10 sec. will be used (use -t <n> to specify execution time)");
    }

    return executionTime;
  }

  private static void checkDatabaseStatus(Properties properties, String databaseUrl) throws SQLException
  {
    if (properties.containsKey("--force-clear"))
    {
      clearDatabase(databaseUrl);
    }

    try
    {
      checkDatabaseEmpty(databaseUrl);
    }
    catch (RuntimeException e)
    {
      System.out.println("ERROR: " + e.getMessage());
      throw new RuntimeException();
    }
  }

  private static void checkDatabaseEmpty(String databaseUrl) throws SQLException, RuntimeException
  {
    Connection c = DriverManager.getConnection(databaseUrl);
    Statement stmt = c.createStatement();
    ResultSet rs = stmt.executeQuery("MATCH (n) RETURN COUNT(n) as total");
    rs.next();
    if (rs.getInt("total") != 0)
    {
      throw new RuntimeException("Database is not empty (use -f to force database clear)");
    }
  }

  private static void clearDatabase(String databaseUrl) throws SQLException
  {
    Connection c = DriverManager.getConnection(databaseUrl);
    Statement stmt = c.createStatement();
    stmt.executeQuery("MATCH (n) DETACH DELETE n");
  }

  private static Properties getProperties(String[] args)
  {
    Properties properties = new Properties();

    boolean nextValue = false;
    String argument = "", value = "";

    for (String arg : args)
    {
      if (nextValue)
      {
         value = arg;
         nextValue = false;
      }
      else if ("-f".equals(arg))
      {
        argument = "--force-clear";
      }
      else if ("-t".equals(arg))
      {
        argument = "--time";
        nextValue = true;
      }
      else if ("-n".equals(arg))
      {
        argument = "--threads";
        nextValue = true;
      }
      else if ("-d".equals(arg))
      {
        argument = "--db-url";
        nextValue = true;
      }
      else if ("-p".equals(arg))
      {
        argument = "--protocol";
        nextValue = true;
      }

      if (!nextValue)
      {
        properties.put(argument, value);
        argument = "";
        value = "";
      }
    }
    return properties;
  }
}
