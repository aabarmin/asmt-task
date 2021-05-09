package com.epam.asmt.ingest.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordLoadStatusHolderTest {
  private RecordLoadStatusHolder uut;

  @BeforeEach
  void setUp() {
    uut = new RecordLoadStatusHolder();
  }

  @Test
  void isLoaded_falseBeforeAll() {
    assertFalse(uut.isLoaded());
  }

  @Test
  void isLoaded_falseAfterFalse() {
    uut.loaded(false);
    assertFalse(uut.isLoaded());
  }

  @Test
  void isLoaded_trueAfterTrue() {
    uut.loaded(true);
    assertTrue(uut.isLoaded());
  }
}