package org.csu.metrics.metric;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;

public class NOCTest extends BaseTest {

	private static CKReport report;
	
	@BeforeClass
	public static void setUp() {
		report = new CK().calculate(fixturesDir() + "/dit");
	}
	
	@Test
	public void should_detect_children() {
		
		CKNumber a = report.getByClassName("dit.A");
		Assert.assertEquals(1, a.getNoc());
		CKNumber b = report.getByClassName("dit.B");
		Assert.assertEquals(2, b.getNoc());
		CKNumber c = report.getByClassName("dit.C");
		Assert.assertEquals(1, c.getNoc());
		CKNumber d = report.getByClassName("dit.D");
		Assert.assertEquals(0, d.getNoc());
	}
}
