package com.perficient.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
 
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;
/**
 * Utility to Transfer a file using FTP server 
 * @author pooja.manna
 *
 */
public class FTPTransfer{
protected ExtentTest test;
	
	public FTPTransfer(ExtentTest extentTest) {
		test = extentTest;
	}
     // Creating FTP Client instance
    FTPClient ftp = null;
    
   /**
    * Establishing Connection with the FTP server 
    * @param host
    * @param port
    * @param username
    * @param password
    * @throws Exception
    */
    public void FTPServerConnection(String host, int port, String username, String password){
        try{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host,port);
        test.log(Status.PASS, "Server Connected");
        System.out.println("FTP URL is:"+ftp.getDefaultPort());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply))	{
            ftp.disconnect();
            test.log(Status.PASS, "Server Disconnected");
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(username, password);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        }
        catch (Exception e) {
			e.printStackTrace();
		}
    }    
 
    /**
     * Method to Upload the file in the specified location with the host directory
     * @param localFileFullName
     * @param fileName
     * @param hostDir
     * @throws Exception
     */
    public void uploadFTPFile(String localFileFullName, String fileName, String hostDir)
            throws Exception
            {
                try {
                InputStream input = new FileInputStream(new File(localFileFullName));
 
                this.ftp.storeFile(hostDir + fileName, input);
                test.log(Status.PASS, "File Uploaded");
                }
                catch(Exception e){
                 e.printStackTrace();
                }
            }
    
   /**
    * Download the file from the uploaded location or from the 
    * Source Director to Destination Directory
    * @param source
    * @param destination
    */
    public void downloadFTPFile(String source, String destination) {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            this.ftp.retrieveFile(source, fos);
            test.log(Status.PASS, "File Downloaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method Checking the File in the Remote Directory
     * @param directory
     * @param fileName
     * @return
     * @throws IOException
     */
    public boolean listFTPFiles(String directory, String fileName) throws IOException {
        // lists files and directories in the current working directory
        boolean verificationFilename = false;        
        FTPFile[] files = ftp.listFiles(directory);
        System.out.println("Number of Files in Directory: "+ files.length);
        for (FTPFile file : files) {
            String details = file.getName();                
            System.out.println(details);            
            if(details.contains(fileName))
            {
            	System.out.println(fileName);
                System.out.println("File Present in the Directory ");
                verificationFilename =details.contains(fileName);
            }
        }  
         return verificationFilename;
    }
    
    // Disconnect the connection to FTP
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
                test.log(Status.PASS, "Server Disconnected");
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }
    
}
    
   