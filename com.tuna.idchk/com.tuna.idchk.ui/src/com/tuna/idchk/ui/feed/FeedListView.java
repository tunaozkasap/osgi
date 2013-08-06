package com.tuna.idchk.ui.feed;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.text.View;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.tuna.idchk.feed.api.IFeed;
import com.tuna.idchk.ui.ui;
import com.tuna.idchk.ui.service.ServiceLocator;

public class FeedListView extends ViewPart{
	
	private Bundle bundle;
	private List<Button> buttons = new ArrayList<Button>();
	private Composite parent;
	
	public FeedListView() {
		bundle = FrameworkUtil.getBundle(FeedListView.class);
		handleFeedServicesUpdateEvent(bundle);
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite newParent = new Composite(parent, SWT.FILL);
		newParent.setLayout(new FillLayout(SWT.VERTICAL));
		this.parent = newParent;
		createContent(ServiceLocator.feedList);
	}
	
	private void clearContent(){
		for (Button button : buttons) {
			button.dispose();
		}
		buttons.clear();
	}
	
	private void createContent(List<IFeed> feedList){
		for(IFeed feed : feedList){
			Button button = new Button(parent, SWT.NONE);
			button.setText(feed.getFeedName());
			button.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent event) {
					try {
						IHandlerService service = (IHandlerService)FeedListView.this.getSite().getService(IHandlerService.class);
						org.eclipse.swt.widgets.Event tmp = new org.eclipse.swt.widgets.Event();
						tmp.item = (Widget) event.getSource();
						service.executeCommand(ui.cmd.openFeedTableEditor, tmp);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			buttons.add(button);
		}
	}
	
	private void handleFeedServicesUpdateEvent(Bundle bundle){
		org.osgi.service.event.EventHandler handler = new org.osgi.service.event.EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				clearContent();
				createContent(ServiceLocator.feedList);
			}
		};
		
		Dictionary<String,String> properties = new Hashtable<String, String>();
		properties.put(org.osgi.service.event.EventConstants.EVENT_TOPIC, ui.event.feedServicesUpdate);
		bundle.getBundleContext().registerService(org.osgi.service.event.EventHandler.class, handler, properties);		
	}

	@Override
	public void setFocus() {
		parent.setFocus();
	}
}
