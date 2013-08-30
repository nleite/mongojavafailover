package mongodb.failover;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FailoverTestTest {

	FailoverTest obj;
	@Mock
	Properties props;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(props.getProperty("hosts")).thenReturn("localhost:27017,localhost:27027,localhost:27007");
		when(props.getProperty("fields")).thenReturn("name,age,gender");
		when(props.getProperty("ndocs")).thenReturn("10000");
		when(props.getProperty("dbname")).thenReturn("ohhbaby");
		when(props.getProperty("collname")).thenReturn("ohhdear");
		
		obj = new FailoverTest(props);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFailoverTest() {
		assertNotNull(obj.getDocGen());
	}
	
	@Test
	public void testRun(){
		int expected = 10000;
		obj.run();
		int actual = obj.getProcessedDocs();
		assertEquals(expected, actual);
	}

}
