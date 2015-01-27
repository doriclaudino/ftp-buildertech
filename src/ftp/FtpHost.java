package ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import files.FileHandler;


public class FtpHost {
	private String identificacao; 
	private String configFolderName;
	private String configFileName;
	private File appFolder;
	private File configFile;
	private File configDir;
	private Properties propriedades;
	private String ftpServerHostname;
	private String ftpServerPort;
	private String ftpServerMainFolder;
	private File downloadListFile;
	private File[] downloadFiles;
	
	public FtpHost(String identificacao) throws URISyntaxException, IOException {
		this.setIdentificacao(identificacao);
		this.setConfigFolderName("resources");
		this.setConfigFileName("connection.properties");
		this.setAppFolder();
		this.setConfigDir();
		this.setConfigFile();
		this.setPropriedades();		
		
		
		this.setFtpMainFolder();
		this.setFtpServerHostname();
		this.setFtpServerPort();
		
		System.out.println("ftp.server.hostname:"+getFtpServerHostname());
		System.out.println("ftp.server.port:"+getFtpServerPort());
		System.out.println("ftp.server.mainfolder:"+getFtpServerMainFolder());
		
		
		this.setDownloadListFile();
		this.setDownloadFiles();
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getConfigFolderName() {
		return configFolderName;
	}

	public void setConfigFolderName(String configFolderName) {
		this.configFolderName = configFolderName;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public File getAppFolder() {
		return appFolder;
	}

	public void setAppFolder() throws URISyntaxException, FileNotFoundException {
		this.appFolder = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();	
		if(!this.getAppFolder().isDirectory() || !this.getAppFolder().exists())
			throw new FileNotFoundException("Directory AppFolder not found!");	
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile() throws FileNotFoundException {
		this.configFile = new File(this.getConfigDir(), this.getConfigFileName());
		if(!this.getConfigFile().isFile() || !this.getConfigFile().exists())
			throw new FileNotFoundException(this.getConfigFolderName() + File.separator + this.getConfigFileName() + " not found!");		
	}

	public File getConfigDir() {
		return configDir;
	}

	public void setConfigDir() throws FileNotFoundException {
		this.configDir = new File(this.getAppFolder(), this.getConfigFolderName());
		if(!this.getConfigDir().isDirectory() || !this.getConfigDir().exists())
			throw new FileNotFoundException(File.separator + this.getConfigFolderName() + " not found!");	
	}

	public Properties getPropriedades() {
		return propriedades;
	}

	public void setPropriedades() throws IOException {
		InputStreamReader in = new InputStreamReader(new FileInputStream(this.getConfigFile().getPath()), "UTF-8");
		
		propriedades = new Properties();
		propriedades.load(in);
	}
	
	
	
	public String getFtpServerHostname() {
		return ftpServerHostname;
	}

	public void setFtpServerHostname() {
		this.ftpServerHostname = this.getPropriedades().getProperty("ftp.server.hostname");
	}

	public String getFtpServerPort() {
		return ftpServerPort;
	}

	public void setFtpServerPort() {
		this.ftpServerPort = this.getPropriedades().getProperty("ftp.server.port");
	}

	public String getFtpServerMainFolder() {
		return ftpServerMainFolder;
	}

	public void setFtpMainFolder() {
		this.ftpServerMainFolder = this.getPropriedades().getProperty("ftp.server.mainfolder");	}


	public File getDownloadListFile() {
		return downloadListFile;
	}

	public void setDownloadListFile() throws FileNotFoundException {
		this.downloadListFile = new File(this.getFtpServerHostname() + File.separator + this.getFtpServerMainFolder() +  File.separator + this.getIdentificacao());
		System.out.println(this.getDownloadListFile());
		if(!this.getDownloadListFile().isFile() || !this.getDownloadListFile().exists())
			throw new FileNotFoundException("ftp: " + this.getFtpServerHostname() + File.separator + this.getFtpServerMainFolder() +  File.separator + this.getIdentificacao() + " not found!");
	}

	public File[] getDownloadFiles() {
		return downloadFiles;
	}

	public void setDownloadFiles() throws IOException {
		FileInputStream fis = new FileInputStream(this.getDownloadListFile().getPath());
		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		File f;
		File copy;
		
		String line = null;
		String para = null;
		String de = null;
		
		
		Pattern de_para = Pattern.compile("<de>(.{1,})</de>.*<para>(.{1,})</para>");
		
		while ((line = br.readLine()) != null) {
			Matcher m = de_para.matcher(line);
			if(m.matches()){
				de = m.group(1);
				para = m.group(2);	
			}
			
			if(File.separator.equalsIgnoreCase("\\") && line.contains("/"))
				line.replace("\\", "//");
			else
				line.replace("//", "\\");
				
			
			System.out.println("de: " + de + "    para: " + para);
			
			f = new File(de);
			if(!f.exists() || !f.isFile())
				throw new FileNotFoundException("Arquivo " + de + " não fisponível no servidor ftp");
			else{
				FileHandler handler = new FileHandler();
				handler.copyFileUsingFileStreams(new File(de), new File(para));
			}
			
		}
	 
		br.close();
	
	}
	
	
	
	
}
