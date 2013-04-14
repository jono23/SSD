package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.Accommodation;

public class AccommodationTest {
	Accommodation accommodation;
	@Before
	public void setUp() throws Exception {
		accommodation = new Accommodation("test", 5, 2, 6, 8, new ArrayList());
	}

	@After
	public void tearDown() throws Exception {
		accommodation = null;
	}

	@Test
	public void testGetFreeRooms() {
		Assert.assertTrue(accommodation.getFreeRooms(new Date((long)System.currentTimeMillis())).size() == 2);
	}

}
