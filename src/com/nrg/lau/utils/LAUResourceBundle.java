package com.nrg.lau.utils;

import java.util.ResourceBundle;

import com.nrg.lau.commons.Constants;

public class LAUResourceBundle {

	public static ResourceBundle getMessage() throws Exception	{
		return ResourceBundle.getBundle(Constants.LAU_PROPERTIES_FILE);
	}
}
