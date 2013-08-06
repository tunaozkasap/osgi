package com.tuna.idchk.ui.feed;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FeedTableEditorInput implements IEditorInput{

	private final String feedName;

    public FeedTableEditorInput(String feedName) {
        this.feedName = feedName;
    }
	
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return feedName;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Editor Input Tooltip";
	}
	
	@Override
    public int hashCode() {
        return feedName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeedTableEditorInput other = (FeedTableEditorInput) obj;
        if (feedName.equalsIgnoreCase(other.feedName))
            return true;
        return false;
    }

}
