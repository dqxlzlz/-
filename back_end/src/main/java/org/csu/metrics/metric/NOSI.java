package org.csu.metrics.metric;

import org.csu.metrics.CKNumber;
import org.csu.metrics.CKReport;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;

public class NOSI extends ASTVisitor implements Metric {

	private int count = 0;

	public boolean visit(MethodInvocation node) {

		IMethodBinding binding = node.resolveMethodBinding();
		if(binding!=null && Modifier.isStatic(binding.getModifiers()))
				count++;
		
		return super.visit(node);
	}

	@Override
	public void setResult(CKNumber result) {
		result.setNosi(count);
	}

	@Override
	public void execute(CompilationUnit cu, CKNumber number, CKReport report) {
		cu.accept(this);
	}
	
}
