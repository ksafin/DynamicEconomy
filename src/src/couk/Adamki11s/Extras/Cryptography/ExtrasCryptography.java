package couk.Adamki11s.Extras.Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ExtrasCryptography extends CryptographyMethods {

	@Override
	public String computeMD5Hash(String s){
		try{
		MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(s.getBytes(),0,s.length());
		return (new BigInteger(1,m.digest()).toString(16)).toString();
		} catch (NoSuchAlgorithmException nsax){
			nsax.printStackTrace();
		}
		return null;
		
	}

	@Override
	public boolean compareHashes(String hash1, String hash2) {
		if(hash1.equals(hash2)){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String computeSHA1Hash(String s){
		try{
		MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(s.getBytes("iso-8859-1"), 0, s.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	 private static String convertToHex(byte[] data) { 
	        StringBuffer buf = new StringBuffer();
	        for (int i = 0; i < data.length; i++) { 
	            int halfbyte = (data[i] >>> 4) & 0x0F;
	            int two_halfs = 0;
	            do { 
	                if ((0 <= halfbyte) && (halfbyte <= 9)) 
	                    buf.append((char) ('0' + halfbyte));
	                else 
	                    buf.append((char) ('a' + (halfbyte - 10)));
	                halfbyte = data[i] & 0x0F;
	            } while(two_halfs++ < 1);
	        } 
	        return buf.toString();
	    }

	@Override
	public String computeSHA2_256BitHash(String s) {
		try{
			String password = s;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			return hexString.toString();
		} catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		return null;
	} 
	
	@Override
	public String computeSHA2_384BitHash(String s) {
		try{
			String password = s;
			MessageDigest md = MessageDigest.getInstance("SHA-384");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			return hexString.toString();
		} catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		return null;
	} 
	
	@Override
	public String computeSHA2_512BitHash(String s) {
		try{
			String password = s;
			MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			return hexString.toString();
		} catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		return null;
	} 

}
