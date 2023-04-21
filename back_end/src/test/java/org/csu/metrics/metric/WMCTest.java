package org.csu.metrics.metric;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;

public class WMCTest extends BaseTest {

	private static CKReport report;
	
	@BeforeClass
	public static void setUp() {
		report = new CK().calculate(fixturesDir() + "/wmc");
	}

	@Test
	public void countAllBranchInstructions() {
		
		CKNumber a = report.getByClassName("wmc.CC1");
		Assert.assertEquals(5, a.getWmc());
		CKNumber b = report.getByClassName("wmc.CC2");
		Assert.assertEquals(5, b.getWmc());
	}
}
