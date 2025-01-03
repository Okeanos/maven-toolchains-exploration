package com.nikolasgrottendieck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaVersionTest {
	@Test
	public void runningSomeJavaVersion() {
		assertTrue(Runtime.version().feature() > 8);
	}
}
