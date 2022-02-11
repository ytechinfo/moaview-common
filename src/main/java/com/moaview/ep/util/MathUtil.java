package com.moaview.ep.util;

import java.util.Random;

public final class MathUtil
{
  public static int randomNextInt(int arg0)
  {
    return new Random().nextInt(arg0);
  }

  public static double random(int arg0) {
    return Math.random() * arg0;
  }

  public static int min(int arg0, int arg1) {
    return Math.min(arg0, arg1);
  }

  public static int max(int arg0, int arg1) {
    return Math.max(arg0, arg1);
  }
}