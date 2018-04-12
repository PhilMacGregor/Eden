package cz.macgregor.eden.util.filecrawler;

import java.io.File;

import cz.macgregor.eden.util.filecrawler.resources.FolderResource;

/**
 * this class resembles a single resolved resource callable by its qualified
 * name.
 * 
 * @author MacGregor
 *
 * @param <T>
 *            type of the resource represented. Fir example: File, Class,
 *            ImageIcon...
 */
public abstract class ResourceEntry<T extends Object> {
	protected FolderResource parent;
	private String qualifiedName;
	private File file;

	public abstract T getResource();

	public FolderResource getParent() {
		return parent;
	}

	public void setParent(FolderResource parent) {
		this.parent = parent;
		parent.getChildren().add(this);
	}

	/**
	 * getter.
	 * 
	 * @return the qualifiedName
	 */
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * setter.
	 * 
	 * @param qualifiedName
	 *            the qualifiedName to set
	 */
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	/**
	 * getter.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * setter.
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		String ret = "Resource entry: ";
		if (qualifiedName == null) {
			ret += "NOT YET INITIALIZED";
		} else if (qualifiedName.isEmpty()) {
			ret += "ROOT FOLDER";
		} else {
			ret += qualifiedName;
		}

		return ret;
	}

}
