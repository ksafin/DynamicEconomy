/**
 * LICENSING
 * 
 * This software is copyright by Adamki11s <adam@adamki11s.co.uk> and is
 * distributed under a dual license:
 * 
 * Non-Commercial Use:
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Commercial Use:
 *    Please contact adam@adamki11s.co.uk
 */

package couk.Adamki11s.AutoUpdater;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class FetchSource {
	
	

	protected static String fetchSource(URL website, Logger l, String prefix){
	    InputStream is = null;
	    DataInputStream dis = null;
	    String s, source = "";

		try {
			is = website.openStream();
		} catch (IOException ex) {
			ex.printStackTrace();
			l.info("[AU]" + prefix + " Error opening URL input stream!");
		}
		
	    dis = new DataInputStream(new BufferedInputStream(is));
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
		
		try {
			while ((s = br.readLine()) != null) {
			    source += s;
			 }
		} catch (IOException ex) {
			ex.printStackTrace();
			l.info("[AU]" + prefix + " Error reading input stream!");
		}
		
		try {
            is.close();
         } catch (IOException ioe) {
        	 ioe.printStackTrace();
        	 l.info("[AU]" + prefix + " Error closing URL input stream!");
         }
         
		return source;
	}
	
	
}
