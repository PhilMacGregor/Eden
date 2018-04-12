package cz.macgregor.eden.util.filecrawler.resources;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import cz.macgregor.eden.util.filecrawler.ResourceEntry;

/**
 * A resource representing a single folder. Contains its children and child
 * folders in separate lists.
 * 
 * @author Filip Gregor
 *
 */
public class FolderResource extends ResourceEntry<File[]> {

	private final Collection<FolderResource> childFolders;

	private final Collection<ResourceEntry<?>> children;

	/**
	 * constructor.
	 */
	public FolderResource() {
		super();
		this.childFolders = new HashSet<>();
		this.children = new HashSet<>();
	}

	@Override
	public File[] getResource() {
		return this.getFile().listFiles();
	}

	/**
	 * add a child folder resource.
	 * 
	 * @param fldRes
	 *            folder resource
	 */
	public void addChildFolder(FolderResource fldRes) {
		this.childFolders.add(fldRes);
		fldRes.setParent(this);
	}

	/**
	 * add a child resource.
	 * 
	 * @param res
	 *            resource
	 */
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
	 * getter.
	 * 
	 * @return the childFolders
	 */
	public Collection<FolderResource> getChildFolders() {
		return childFolders;
	}

	/**
	 * getter.
	 * 
	 * @return the children
	 */
	public Collection<ResourceEntry<?>> getChildren() {
		return children;
	}

}
