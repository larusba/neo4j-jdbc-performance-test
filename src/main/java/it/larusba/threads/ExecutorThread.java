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

package it.larusba.threads;

import it.larusba.utils.Utils;

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
