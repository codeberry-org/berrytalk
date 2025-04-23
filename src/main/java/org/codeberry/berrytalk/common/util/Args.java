package org.codeberry.berrytalk.common.util;

import java.util.Collection;

public class Args {

  public static <T> T requireNotNull(T arg, String name) {
    if (arg == null) {
      throw new IllegalArgumentException(String.format("'%s' cannot be null", name));
    }
    return arg;
  }

  public static <T extends CharSequence> T requireNotEmpty(T arg, String name) {
    requireNotNull(arg, name);
    if (arg.isEmpty()) {
      throw new IllegalArgumentException(String.format("'%s' cannot be empty", name));
    }
    return arg;
  }

  public static <T extends Collection<?>> T requireNotEmpty(T arg, String name) {
    requireNotNull(arg, name);
    if (arg.isEmpty()) {
      throw new IllegalArgumentException(String.format("'%s' cannot be empty", name));
    }
    return arg;
  }

  public static <T> T[] requireNotEmpty(T[] arg, String name) {
    requireNotNull(arg, name);
    if (arg.length == 0) {
      throw new IllegalArgumentException(String.format("'%s' cannot be empty", name));
    }
    return arg;
  }
}
