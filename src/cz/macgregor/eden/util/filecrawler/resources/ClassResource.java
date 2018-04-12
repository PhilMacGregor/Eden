package cz.macgregor.eden.util.filecrawler.resources;

import cz.macgregor.eden.util.filecrawler.ResourceEntry;

public class ClassResource extends ResourceEntry<Class<?>> {

	@Override
	public Class<?> getResource() {
		try {
			String path = this.getQualifiedName();
			path = path.substring(0, path.lastIndexOf("."));
			return Class.forName(path);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load class resource: " + this.getQualifiedName(), e);
		}
	}

}
