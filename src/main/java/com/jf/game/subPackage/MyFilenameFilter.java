package com.jf.game.subPackage;

import java.io.File;
import java.io.FilenameFilter;

/**
 * find Target File or dir
 * @author sun
 * TODO
 * Sep 21, 2017
 */
public class MyFilenameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		if(dir!=null && dir.isDirectory() && name.endsWith(".app")){ 
			 return true;
		}
		return false;
	}

}
