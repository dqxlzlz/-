package org.csu.metrics.metric;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;

public class LOCTest extends BaseTest {

	private static CKReport report;
	
	@BeforeClass
	public static void setUp() {
		report = new CK().calculate(fixturesDir() + "/cbo");
	}
	
	@Test
	public void countLinesIgnoringEmptyLines() {
		CKNumber a = report.getByClassName("cbo.Coupling1");
		Assert.assertEquals(11, a.getLoc());
	}
	
}
