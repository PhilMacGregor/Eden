package cz.macgregor.eden.util.filecrawler.resources;

import cz.macgregor.eden.util.filecrawler.ResourceEntry;

/**
 * the resource for files with unknown extensions. Its resource only returns
 * Object (currently null).
 * 
 * @author MacGregor
 *
 */
public class UnknownResource extends ResourceEntry<Object> {

	@Override
	public Object getResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
