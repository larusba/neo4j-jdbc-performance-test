package it.larusba.neo4j.jdbc.threads;

import it.larusba.neo4j.jdbc.utils.Utils;

public abstract class ExecutorThread implements Runnable
{
  protected ThreadType                type;

  protected boolean                   running          = false;

  protected int                       queriesExecuted  = 0;

  protected float                     averageQueryTime = 0;

  protected abstract void executeQuery();
  
  protected abstract void updateStatement();

  @Override
  public void run()
  {
    while (!Utils.isRunning())
    {
    }

    this.running = true;

    try
    {
      while (this.running)
      {
        long time = System.currentTimeMillis();
        this.executeQuery();
        time = System.currentTimeMillis() - time;
        updateStatement();
        this.averageQueryTime = (this.averageQueryTime * this.queriesExecuted + time) / ++this.queriesExecuted;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public ThreadType getType()
  {
    return this.type;
  }

  public void stop()
  {
    this.running = false;
  }

  public int getExecutedQueries()
  {
    return this.queriesExecuted;
  }

  public float getAverageQueryTime()
  {
    return this.averageQueryTime;
  }
}