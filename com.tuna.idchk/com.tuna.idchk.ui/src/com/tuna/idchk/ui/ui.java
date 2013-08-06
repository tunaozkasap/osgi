package com.tuna.idchk.ui;

public class ui {
	
	public static final cmd cmd = new cmd();
	public static final event event = new event();
	public static final editor editor = new editor();
	public static final view view = new view();
	
	public static class view{
		public String feedListView = "com.tuna.idchk.ui.editor.view.feedList";
	}
	
	public static class editor{
		public String feedTableEditor = "com.tuna.idchk.ui.editor.feedTable";
	}
	
	public static class cmd{
		public String openFeedTableEditor = "com.tuna.idchk.ui.editor.openFeedTable";
	}
	
	public static class event{
		public String feedServicesUpdate = "serviceUpdate/feeds";
	}
}
