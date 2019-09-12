package de.ollie.archimedes;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore("Until the headless problem is solved.")
public class ArchimedesApplicationTests {

	@Before
	public void setUp() {
		System.setProperty("java.awt.headless", "false");
	}

	@Test
	public void contextLoads() {
	}

}
