package cz.macgregor.eden.util.filecrawler.resources;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import cz.macgregor.eden.util.filecrawler.ResourceEntry;

public class FolderResource extends ResourceEntry<File[]> {
	
	private final Collection<FolderResource> childFolders;
	
	private final Collection<ResourceEntry<?>> children;

	public FolderResource() {
		super();
		this.childFolders = new HashSet<>();
		this.children = new HashSet<>();
	}
	
	@Override
	public File[] getResource() {
		return this.getFile().listFiles();
	}
	
	public void addChildFolder(FolderResource fldRes) {
		this.childFolders.add(fldRes);
		fldRes.setParent(this);
	}
	
	public void addChild(ResourceEntry<?> res) {
		this.children.add(res);
		res.setParent(this);
	}
	
	@Override
	public void setParent(FolderResource parent) {
		this.parent = parent;
		parent.childFolders.add(this);
	}

	/**
	 * @return the childFolders
	 */
	public Collection<FolderResource> getChildFolders() {
		return childFolders;
	}

	/**
	 * @return the children
	 */
	public Collection<ResourceEntry<?>> getChildren() {
		return children;
	}

}
