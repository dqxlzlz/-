package org.csu.metrics.metric;

import org.csu.metrics.CK;
import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CBOTest extends BaseTest {

	private static CKReport report;
	
	@BeforeClass
	public static void setUp() {
		report = new CK().calculate(fixturesDir() + "/cbo");
	}
	
	@Test
	public void ignoreJavaTypes() {
		CKNumber a = report.getByClassName("cbo.Coupling0");
		Assert.assertEquals(0, a.getCbo());
	}
	
	@Test
	public void countDifferentPossibilitiesOfDependencies() {
		
		CKNumber a = report.getByClassName("cbo.Coupling1");
		Assert.assertEquals(7, a.getCbo());
	}
	
	@Test
	public void countEvenWhenNotResolved() {
		
		CKNumber a = report.getByClassName("cbo.Coupling3");
		Assert.assertEquals(1, a.getCbo());
	}
	
	@Test
	public void countInterfacesAndInheritances() {
		
		CKNumber b = report.getByClassName("cbo.Coupling2");
		Assert.assertEquals(5, b.getCbo());
	}

	@Test
	public void countClassCreations() {
		
		CKNumber b = report.getByClassName("cbo.Coupling4");
		Assert.assertEquals(3, b.getCbo());
	}

	@Test
	public void countMethodParameters() {
		
		CKNumber b = report.getByClassName("cbo.MethodParams");
		Assert.assertEquals(2, b.getCbo());
	}

}
