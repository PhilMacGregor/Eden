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

public class FileCrawler {

	private static FileCrawler instance;

	private Map<String, ResourceEntry<?>> resources;
	private Map<String, FolderResource> folders;

	private Map<String, Class<? extends ResourceEntry<?>>> extensionsMap;

	public static FileCrawler getInstance() {
		if (instance == null) {
			instance = new FileCrawler();
		}
		return instance;
	}

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
	
	public Collection<ResourceEntry<?>> searchFolder(String qualifiedName, String extension) {
		return searchFolder(qualifiedName, extension, true);
	}
	
	public Collection<ResourceEntry<?>> searchFolder(String qualifiedName) {
		return searchFolder(qualifiedName, null, true);
	}

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

	private FolderResource addFolderResource(File file, String qualifiedName, FolderResource parent) {
		FolderResource fldRes = new FolderResource();
		fldRes.setFile(file);
		fldRes.setQualifiedName(qualifiedName);
		fldRes.setParent(parent);

		resources.put(qualifiedName, fldRes);
		folders.put(qualifiedName, fldRes);
		return fldRes;
	}

	private String getFileExtension(File file) {
		String fName = file.getName();
		int extensionStart = fName.lastIndexOf(".");
		if (extensionStart < 0) {
			return "";
		}
		return fName.substring(extensionStart);
	}

	private void initExtensions() {
		extensionsMap.put(".png", ImageResource.class);
		extensionsMap.put(".class", ClassResource.class);
	}

	private Class<?> findResourceForExtension(String extension) {
		Class<?> clazz = extensionsMap.get(extension);
		if (clazz == null) {
			clazz = UnknownResource.class;
		}
		return clazz;
	}

}
