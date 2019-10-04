package com.example.demo.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class represents test case for testing properties of MossReport Model
 * 
 * @author sandipsamal
 *
 */
public class MossReportTest {
	/**
	 * Test the id property
	 */
	@Test
	public void testId() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser1SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(1L, mr.getMossId(), 0);
	}

	/**
	 * Test for User1 id
	 */
	@Test
	public void testU1id() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser1SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(1L, mr.getUser1Id(), 0);
	}

	/**
	 * Test for User2 id
	 */
	@Test
	public void testU2id() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser1SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(1L, mr.getUser2Id(), 0);
	}

	/**
	 * Test for user1submissionid
	 */
	@Test
	public void testU1Sid() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser1SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(1L, mr.getUser1SubId(), 0);
	}

	/**
	 * Test for user2submission Id
	 */
	@Test
	public void testU2Sid() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(1L, mr.getUser2SubId(), 0);
	}

	/**
	 * test for assignment property
	 */
	@Test
	public void testAssign() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(assgn, mr.getAssignment());
	}

	/**
	 * Test for moss link property
	 */
	@Test
	public void testMosLink() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals("src", mr.getMossLink());
	}

	/**
	 * test for plagiarism score property
	 */
	@Test
	public void testPScore() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(8.9, mr.getPlagiarismScore(), 0);
	}

	/**
	 * Test for lines copied property
	 */
	@Test
	public void testLinesCopied() {
		Assignment assgn = new Assignment();
		MossReport mr = new MossReport();
		mr.setMossId(1L);
		mr.setLinesCopied(8);
		mr.setUser1Id(1L);
		mr.setUser2Id(1L);
		mr.setUser1SubId(1L);
		mr.setUser2SubId(1L);
		mr.setAssignment(assgn);
		mr.setPlagiarismScore(8.9);
		mr.setMossLink("src");
		assertEquals(8, mr.getLinesCopied(), 0);
	}

}
