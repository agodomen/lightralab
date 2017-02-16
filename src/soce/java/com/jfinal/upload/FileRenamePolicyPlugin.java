package com.jfinal.upload;

import java.io.File;

import com.oreilly.servlet.multipart.FileRenamePolicy;

import edu.muc.platform.plugin.tools.ToolUtils;

public class FileRenamePolicyPlugin implements FileRenamePolicy {

	@Override
	public File rename(File file) {
        String ext = "";   
        int pot = file.getName().lastIndexOf(".");   
        if(pot != -1){   
        	ext = file.getName().substring(pot);   
        }else{   
        	ext = "";   
        }   
        String newName = ToolUtils.getUuidByJdk(true) + ext;   
        file = new File(file.getParent(),newName);   
        return file;
	}

}
