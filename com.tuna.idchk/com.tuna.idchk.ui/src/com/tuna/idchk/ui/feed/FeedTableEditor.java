package com.tuna.idchk.ui.feed;

import javax.print.attribute.standard.MediaPrintableArea;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class FeedTableEditor extends EditorPart{
	private TableViewer feedTableViewer;
	private FeedTableEditorInput input;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof FeedTableEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (FeedTableEditorInput) input;
		setSite(site);
		setInput(input);
		setPartName(input.getName());
	}
	
	@Override
	public void createPartControl(Composite parent) {
		feedTableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		feedTableViewer.getTable().setHeaderVisible(true);
		feedTableViewer.getTable().setLinesVisible(true);
		col(feedTableViewer, "First Name");
		col(feedTableViewer, "Last Name");
		col(feedTableViewer, "Gender");
		col(feedTableViewer, "Birth Date");
		feedTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.getEditorInput();
	}
	
	public void col(TableViewer tableViewer, String text){
		TableColumn tc = new TableColumn(tableViewer.getTable(), SWT.NONE);
	    tc.setText(text);
	}

	@Override
	public void setFocus() {
		feedTableViewer.getTable().setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
