package couk.Adamki11s.Extras.Trace;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public abstract class TraceMethods {
	
	/**
	 * Starts the trace.
	 */
	public abstract void startTrace();
	
	/**
	 * Stops the trace.
	 */
	public abstract void stopTrace();
	
	/**
	 * Get the trace time.
	 * @return Time between start and end of trace in milliseconds(ms).
	 */
	public abstract long getTrace();

	/**
	 * Log the result of the latest trace to the console.
	 */
	public abstract void logTraceTime();
	
	/**
	 * Log the trace time with a description to a file.
	 * @param file The location to which the trace will be printed
	 * @param description The accompanying description of the trace.
	 */
	public abstract void logTraceTime(File file, String description);
	
	/**
	 * Broadcast the trace time to the server.
	 * @param s The server to broadcast to.
	 */
	public abstract void broadcastTraceTime(Server s);
	
	/**
	 * Broadcast the trace time to the server with a custom prefix.
	 * @param s The server to broadcast to.
	 */
	public abstract void broadcastTraceTimeCustom(Server s, String prefix);
	
	/**
	 * Send the trace time to a player.
	 * @param p The player to send the trace time to.
	 */
	public abstract void sendTraceTime(Player p);
	
	/**
	 * Send the trace time to a player with a custom prefix.
	 * @param p The player to send the trace time to.
	 */
	public abstract void sendTraceTimeCustom(Player p, String prefix);

}
