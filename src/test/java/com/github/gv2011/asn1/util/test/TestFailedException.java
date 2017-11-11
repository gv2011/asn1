package com.github.gv2011.asn1.util.test;

public class TestFailedException
    extends RuntimeException {
  private static final long serialVersionUID = 759603690774708659L;
  private final TestResult        _result;

  public TestFailedException(
      final TestResult result) {
    _result = result;
  }

  public TestResult getResult() {
    return _result;
  }
}
