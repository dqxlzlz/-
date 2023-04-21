package org.csu.metrics.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;

public class NOF extends ASTVisitor implements Metric {

	private int fields;

	@Override
	public boolean visit(FieldDeclaration node) {
		fields++;

		return false;
	}

	@Override
	public void execute(CompilationUnit cu, CKNumber number, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setResult(CKNumber result) {
		result.setNof(fields);
	}
}
