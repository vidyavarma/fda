package com.nrg.lau.scheduler;


import java.io.File;
import java.util.List;

import com.nrg.lau.beans.LauJobScheduler;

public interface FolderWatch {
	public List<File> fileCreated() throws Exception;	
	public boolean fileDelete(File file) throws Exception;
	public List<LauJobScheduler> getActiveJobs(String status) throws Exception;
	public void insertJob(Object[] params) throws Exception;
	public void updateJobStatus(LauJobScheduler lauJobScheduler) throws Exception;
	public Object getOutput(String threadId) throws Exception;
}
