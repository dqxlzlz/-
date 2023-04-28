package com.sqrrow.metric;


import com.sqrrow.entity.CKNumber;
import com.sqrrow.entity.CKReport;
import org.eclipse.jdt.core.dom.CompilationUnit;

public interface Metric {

	void execute(CompilationUnit cu, CKNumber result, CKReport report);
	void setResult(CKNumber result);
}
