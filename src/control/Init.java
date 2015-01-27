package control;

import ftp.FtpHost;

public class Init {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FtpHost ftp;
		try {
			 ftp = new FtpHost(args[0]);
			 System.out.println("Host:" + ftp.getIdentificacao());
			 System.out.println("AppFolder:" + ftp.getAppFolder());
			 System.out.println("ConfigFolder:" + ftp.getConfigFolderName());
			 System.out.println("ConfigFile:" + ftp.getConfigFileName());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Erro 01 - Identifique este host.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
