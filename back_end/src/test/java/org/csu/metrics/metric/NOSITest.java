package org.csu.metrics.metric;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;

public class NOSITest extends BaseTest {

	private static CKReport report;
	
	@BeforeClass
	public static void setUp() {
		report = new CK().calculate(fixturesDir() + "/nosi");
	}
	
	@Test
	public void staticInvocations() {
		CKNumber a = report.getByClassName("nosi.Class2");
		Assert.assertEquals(1, a.getNosi());
	}

	@Test
	public void staticInvocationsToMethodsInTheSameClass() {
		CKNumber a = report.getByClassName("nosi.Class3");
		Assert.assertEquals(2, a.getNosi());
	}

	@Test
	public void doesNotUnderstandWhenNotResolvable() {
		CKNumber a = report.getByClassName("nosi.Class1");
		Assert.assertEquals(0, a.getNosi());
	}
}
