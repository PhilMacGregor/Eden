package cz.macgregor.eden.util.filecrawler;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cz.macgregor.eden.util.filecrawler.resources.ClassResource;
import cz.macgregor.eden.util.filecrawler.resources.FolderResource;
import cz.macgregor.eden.util.filecrawler.resources.ImageResource;
import cz.macgregor.eden.util.filecrawler.resources.UnknownResource;

/**
 * This class provides access directly to all packages and files in the
 * classpath. The packages can be scanned for child directories and files which
 * are then mapped with resembling instances of ResourceEntry. Their content can
 * then be directly loaded via getResource() method.<br>
 * <br>
 * !!!WARNING: currently does not work with built jars as it cannot reach
 * resources directly in the jar.
 * 
 * @author MacGregor
 *
 */
public class FileCrawler {
	/** the instance. */
	private static FileCrawler instance;

	/** resources being mapped. */
	private Map<String, ResourceEntry<?>> resources;
	/** folders. */
	private Map<String, FolderResource> folders;

	/**
	 * map for the extension. The file type and appropriate instance of
	 * ResourceEntry to be used is determined by the file extension.
	 */
	private Map<String, Class<? extends ResourceEntry<?>>> extensionsMap;

	/**
	 * get instance.
	 * 
	 * @return the instance
	 */
	public static FileCrawler getInstance() {
		if (instance == null) {
			instance = new FileCrawler();
		}
		return instance;
	}

	/**
	 * find a folder in mapped folders according to its qualified name and
	 * return all its resources for a given extension.
	 * 
	 * @param qualifiedName
	 *            qualified name to lookup
	 * @param extension
	 *            extension to be used. If null, all resources will be returned
	 * @param deep
	 *            if true, return content of subfolders too
	 * @return found resources
	 */
	public Collection<ResourceEntry<?>> searchFolder(String qualifiedName, String extension, boolean deep) {
		Collection<ResourceEntry<?>> resolvedResources = new HashSet<>();
		FolderResource fldRes = folders.get(qualifiedName);

		if (extension == null) {
			resolvedResources.addAll(fldRes.getChildren());
		} else {
			Class<?> clazz = findResourceForExtension(extension);
			for (ResourceEntry<?> res : fldRes.getChildren()) {
				if (res.getClass().equals(clazz)) {
					resolvedResources.add(res);
				}
			}
		}

		if (deep) {
			for (FolderResource childFolder : fldRes.getChildFolders()) {
				resolvedResources.addAll(searchFolder(childFolder.getQualifiedName(), extension, deep));
			}
		}

		return resolvedResources;
	}

	/**
	 * convenience method providing deep searchFolder.
	 * 
	 * @param qualifiedName
	 *            qualified name to lookup
	 * @param extension
	 *            extension to be used. If null, all resources will be returned
	 * @return found resources
	 */
	public Collection<ResourceEntry<?>> searchFolder(String qualifiedName, String extension) {
		return searchFolder(qualifiedName, extension, true);
	}

	/**
	 * convenience method providing deep searchFolder for all extensions.
	 * 
	 * @param qualifiedName
	 *            qualified name to lookup
	 * @return found resources
	 */
	public Collection<ResourceEntry<?>> searchFolder(String qualifiedName) {
		return searchFolder(qualifiedName, null, true);
	}

	/**
	 * constructor.
	 */
	private FileCrawler() {
		this.resources = new HashMap<>();
		this.folders = new HashMap<>();
		this.extensionsMap = new HashMap<>();
		initExtensions();

		try {
			URL url = this.getClass().getClassLoader().getResource("");
			System.out.println("FileCrawler searching folders: " + url);
			File rootFld = new File(url.toURI());
			FolderResource fldRes = addFolderResource(rootFld, "", new FolderResource());
			lookupFolder(fldRes, rootFld, "");

		} catch (URISyntaxException | InstantiationException | IllegalAccessException e) {
			System.out.println("Failed to lookup the source folders.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * search a given folder and map its content to a given FolderResource.
	 * 
	 * @param fldRes
	 *            FolderResource to add the content to
	 * @param rootFld
	 *            root folder to lookup
	 * @param qualifiedName
	 *            qualified name to add
	 * @throws InstantiationException
	 *             error occured during mapping
	 * @throws IllegalAccessException
	 *             error occured during mapping
	 */
	private void lookupFolder(FolderResource fldRes, File rootFld, String qualifiedName)
			throws InstantiationException, IllegalAccessException {
		File[] files = rootFld.listFiles();

		for (File file : files) {

			String newQual = qualifiedName + (qualifiedName.isEmpty() ? "" : ".") + file.getName();
			System.out.println(newQual);

			if (file.isDirectory()) {
				FolderResource newFldRes = addFolderResource(file, newQual, fldRes);
				lookupFolder(newFldRes, file, newQual);
			} else {
				addResource(file, newQual, fldRes);
			}

		}
	}

	/**
	 * add a single resource to a given FolderResource based on a given file.
	 * 
	 * @param file
	 *            file to be mapped
	 * @param qualifiedName
	 *            qualified name to be used
	 * @param parent
	 *            parent FolderResource
	 * @throws InstantiationException
	 *             failed to instantiate the ResourceEntry class to be used
	 * @throws IllegalAccessException
	 *             failed to instantiate the ResourceEntry class to be used
	 */
	private void addResource(File file, String qualifiedName, FolderResource parent)
			throws InstantiationException, IllegalAccessException {
		ResourceEntry<?> resource = null;

		String extension = getFileExtension(file);
		Class<?> clazz = findResourceForExtension(extension);

		resource = (ResourceEntry<?>) clazz.newInstance();
		resource.setFile(file);
		resource.setQualifiedName(qualifiedName);
		resource.setParent(parent);

		resources.put(qualifiedName, resource);
	}

	/**
	 * add a new FolderResource resembling to a given file.
	 * 
	 * @param file
	 *            file to be mapped
	 * @param qualifiedName
	 *            qualified name to be used
	 * @param parent
	 *            parent FolderResource
	 * @return FolderResource created
	 */
	private FolderResource addFolderResource(File file, String qualifiedName, FolderResource parent) {
		FolderResource fldRes = new FolderResource();
		fldRes.setFile(file);
		fldRes.setQualifiedName(qualifiedName);
		fldRes.setParent(parent);

		resources.put(qualifiedName, fldRes);
		folders.put(qualifiedName, fldRes);
		return fldRes;
	}

	/**
	 * get an extension of a file.
	 * 
	 * @param file
	 *            file
	 * @return file extension
	 */
	private String getFileExtension(File file) {
		String fname = file.getName();
		int extensionStart = fname.lastIndexOf(".");
		if (extensionStart < 0) {
			return "";
		}
		return fname.substring(extensionStart);
	}

	/**
	 * populate the extensionsMap with ResourceEntry to be used for every
	 * extension.
	 */
	private void initExtensions() {
		extensionsMap.put(".png", ImageResource.class);
		extensionsMap.put(".class", ClassResource.class);
	}

	/**
	 * find an appropriate ResourceEntry class for an extension.
	 * 
	 * @param extension
	 *            extension
	 * @return ResourceEntry class to be used for the extension
	 */
	private Class<?> findResourceForExtension(String extension) {
		Class<?> clazz = extensionsMap.get(extension);
		if (clazz == null) {
			clazz = UnknownResource.class;
		}
		return clazz;
	}

}
