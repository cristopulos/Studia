import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import so.lab4.Thread;
import so.lab4.exceptions.NoFramesToRemoveException;
import so.lab4.exceptions.NoFreeFramesException;
import so.lab4.exceptions.RequestsNotGeneratedException;
import so.lab4.framesDistributor.EqualFramesDistributor;
import so.lab4.framesDistributor.ProportionalFrameDistributor;

public class EqualDistributorTests {

	ArrayList<Thread> threadList;
	@Before
	public void before() {
		threadList =  new ArrayList<Thread>();
		threadList.add(new Thread(20));
		threadList.add(new Thread(30));
	}
	
	@Test 
	public void test1() throws RequestsNotGeneratedException, NoFreeFramesException, NoFramesToRemoveException
	{
		EqualFramesDistributor dist = new EqualFramesDistributor(10, threadList);
		dist.runSimulation(1);
		assertEquals(5,threadList.get(0).getFramesQuant());
	}
	
	@Test 
	public void test2() throws RequestsNotGeneratedException, NoFreeFramesException, NoFramesToRemoveException
	{
		ProportionalFrameDistributor dist = new ProportionalFrameDistributor(10, threadList);
		dist.runSimulation(1);
		assertEquals(4,threadList.get(0).getFramesQuant());
	}

}
