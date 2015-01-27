package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler {

	public boolean fileExists(URL url){
		File f = null;
		try {
			f = new File(url.toURI());
			return f.exists();
		} catch (Exception e) {
			return false;
		}		
	}	
	
	public void copyFileUsingFileStreams(File source, File dest)
	        throws IOException {
	    InputStream input = null;
	    OutputStream output = null;
	    
	    if(!dest.getParentFile().exists())
	    	new File(dest.getParent()).mkdir();
	    
	    try {
	        input = new FileInputStream(source);
	        output = new FileOutputStream(dest);
	        byte[] buf = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = input.read(buf)) > 0) {
	            output.write(buf, 0, bytesRead);
	        }
	    }
        catch (Exception ex){
        	System.out.println(ex.getMessage());	        
	    } finally {
	        input.close();
	        output.close();
	    }
	}
}
