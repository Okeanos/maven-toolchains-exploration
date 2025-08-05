package nikolasgrottendieck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaVersionTest {
	@Test
	public void runningCorrectJavaVersion() {
		assertEquals(21, Runtime.version().feature());
	}
}
