package com.tuna.flickr.ui.intro;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NavigationView extends ViewPart {

	private CLabel comp;
	
	@Override
	public void createPartControl(Composite parent) {
		comp = new CLabel(parent, SWT.NONE);
		comp.setText("TETETETETETETETET              adsfsdfsdf");
		comp.setToolTipText("Tooltip");
		comp.setVisible(true);
	}

	@Override
	public void setFocus() {
		comp.setFocus();
	}

}
