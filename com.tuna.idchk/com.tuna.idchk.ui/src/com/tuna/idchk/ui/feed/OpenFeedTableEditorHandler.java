package com.tuna.idchk.ui.feed;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tuna.idchk.ui.ui;

public class OpenFeedTableEditorHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    Event widgetEvent = (Event) event.getTrigger();
	    Button button = (Button) widgetEvent.item;
	    
	    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
	    IWorkbenchPage page = window.getActivePage();
	    try {
			page.openEditor(new FeedTableEditorInput(button.getText()), ui.editor.feedTableEditor, true);
		} catch (PartInitException ex) {
			throw new RuntimeException(ex);
		}
		return null;
	}

}
