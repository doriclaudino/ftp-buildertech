package ftp;

import java.io.File;

public class FtpHandler {
	private File ftp_servename;

	public FtpHandler() {
		super();
	}

	public File getFtp_servename() {
		return ftp_servename;
	}

	public void setFtp_servename(File ftp_servename) {
		this.ftp_servename = ftp_servename;
	}	
	
}
