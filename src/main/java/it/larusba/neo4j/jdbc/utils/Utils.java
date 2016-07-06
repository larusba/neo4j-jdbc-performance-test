package it.larusba.neo4j.jdbc.utils;

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