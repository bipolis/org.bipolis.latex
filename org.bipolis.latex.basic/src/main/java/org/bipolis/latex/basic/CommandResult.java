package org.bipolis.latex.basic;

public class CommandResult {

  private final String outs;
  private final String errors;

  public CommandResult(String outs, String errors) {
    this.outs = outs;
    this.errors = errors;
  }

  public String getOuts() {
    return outs;
  }

  public String getErrors() {
    return errors;
  }

  public boolean hasErrors() {
    return has(errors);
  }

  public boolean hasOuts() {
    return has(outs);
  }

  private static boolean has(String value) {
    return value != null && !value.isEmpty();

  }

}
