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

package it.larusba.utils;

import java.util.Random;

public class Utils
{
  private static boolean  START   = false;

  private static int      USER_ID = 1;

  private static Random   random  = new Random();

  private static String[] NAMES   = { "AMELIA", "OLIVIA", "EMILY", "AVA", "ISLA", "JESSICA", "POPPY", "ISABELLA", "SOPHIE", "MIA", "RUBY", "LILY", "GRACE", "EVIE", "SOPHIA", "ELLA", "SCARLETT", "CHLOE", "ISABELLE", "FREYA", "CHARLOTTE", "SIENNA",
      "DAISY", "PHOEBE", "MILLIE", "EVA", "ALICE", "LUCY", "FLORENCE", "SOFIA", "LAYLA", "LOLA", "HOLLY", "IMOGEN", "MOLLY", "MATILDA", "LILLY", "ROSIE", "ELIZABETH", "ERIN", "MAISIE", "LEXI", "ELLIE", "HANNAH", "EVELYN", "ABIGAIL", "ELSIE", "SUMMER",
      "MEGAN", "JASMINE", "MAYA", "AMELIE", "LACEY", "WILLOW", "EMMA", "BELLA", "ELEANOR", "ESME", "ELIZA", "GEORGIA", "HARRIET", "GRACIE", "ANNABELLE", "EMILIA", "AMBER", "IVY", "BROOKE", "ROSE", "ANNA", "ZARA", "LEAH", "MOLLIE", "MARTHA", "FAITH",
      "HOLLIE", "AMY", "BETHANY", "VIOLET", "KATIE", "MARYAM", "FRANCESCA", "JULIA", "MARIA", "DARCEY", "ISABEL", "TILLY", "MADDISON", "VICTORIA", "ISOBEL", "NIAMH", "SKYE", "MADISON", "DARCY", "AISHA", "BEATRICE", "SARAH", "ZOE", "PAIGE", "HEIDI",
      "LYDIA", "SARA", "OLIVER", "JACK", "HARRY", "JACOB", "CHARLIE", "THOMAS", "OSCAR", "WILLIAM", "JAMES", "GEORGE", "ALFIE", "JOSHUA", "NOAH", "ETHAN", "MUHAMMAD", "ARCHIE", "LEO", "HENRY", "JOSEPH", "SAMUEL", "RILEY", "DANIEL", "MOHAMMED",
      "ALEXANDER", "MAX", "LUCAS", "MASON", "LOGAN", "ISAAC", "BENJAMIN", "DYLAN", "JAKE", "EDWARD", "FINLEY", "FREDDIE", "HARRISON", "TYLER", "SEBASTIAN", "ZACHARY", "ADAM", "THEO", "JAYDEN", "ARTHUR", "TOBY", "LUKE", "LEWIS", "MATTHEW", "HARVEY",
      "HARLEY", "DAVID", "RYAN", "TOMMY", "MICHAEL", "REUBEN", "NATHAN", "BLAKE", "MOHAMMAD", "JENSON", "BOBBY", "LUCA", "CHARLES", "FRANKIE", "DEXTER", "KAI", "ALEX", "CONNOR", "LIAM", "JAMIE", "ELIJAH", "STANLEY", "LOUIE", "JUDE", "CALLUM", "HUGO",
      "LEON", "ELLIOT", "LOUIS", "THEODORE", "GABRIEL", "OLLIE", "AARON", "FREDERICK", "EVAN", "ELLIOTT", "OWEN", "TEDDY", "FINLAY", "CALEB", "IBRAHIM", "RONNIE", "FELIX", "AIDEN", "CAMERON", "AUSTIN", "KIAN", "RORY", "SETH", "ROBERT", "ALBERT", "SONNY" };

  public static synchronized int getNextUserId()
  {
    return USER_ID++;
  }

  public static synchronized boolean isRunning()
  {
    return START;
  }

  public static void start()
  {
    START = true;
  }
  
  public static synchronized int getRandomUserId()
  {
    return random.nextInt(USER_ID);
  }

  public static String getRandomName()
  {
    return NAMES[random.nextInt(NAMES.length)];
  }

  public static int getRandomAge()
  {
    return random.nextInt(80) + 18;
  }
  
  public static synchronized int getCurrentUsersNumber() {
    return USER_ID;
  }
}
