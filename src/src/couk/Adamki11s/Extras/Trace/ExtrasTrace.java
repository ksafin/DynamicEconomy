package couk.Adamki11s.Extras.Trace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import couk.Adamki11s.Extras.Extras.Extras;

public class ExtrasTrace extends TraceMethods {

	private long startTrace = 0, endTrace = 0;

	@Override
	public void startTrace() {
		// TODO Auto-generated method stub
		startTrace = System.nanoTime();
	}

	@Override
	public void stopTrace() {
		// TODO Auto-generated method stub
		endTrace = System.nanoTime() - startTrace;
	}

	@Override
	public long getTrace() {
		// TODO Auto-generated method stub
		return (endTrace / 1000);
	}

	@Override
	public void logTraceTime() {
		// TODO Auto-generated method stub
		System.out.println("[Extras] Trace time : " + getTrace() + "ms");
	}

	@Override
	public void logTraceTime(File file, String description){
		// TODO Auto-generated method stub
		try{
			FileWriter fstream = new FileWriter(file, true);
	        BufferedWriter bw = new BufferedWriter(fstream);
	        bw.write("[Extras] Trace : " + description + " : " + getTrace() + "ms.");
	        bw.newLine();
	        bw.close();
		} catch (IOException ex){
			System.out.println("[Extras] Could not print trace to file! Caused by plugin : " + Extras.pluginName);
			ex.printStackTrace();
		}
	}

	@Override
	public void broadcastTraceTime(Server s) {
		s.broadcastMessage("Trace time : " + getTrace());
	}

	@Override
	public void sendTraceTime(Player p) {
		p.sendMessage("Trace time : " + getTrace());
		
	}

	@Override
	public void broadcastTraceTimeCustom(Server s, String prefix) {
		s.broadcastMessage(prefix + getTrace());
	}

	@Override
	public void sendTraceTimeCustom(Player p, String prefix) {
		p.sendMessage(prefix + getTrace());
	}

}
