package couk.Adamki11s.Extras.Scheduler;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public abstract class SchedulerMethods {
	
	/**
	 * Initialise the extras scheduler so you can schedule tasks.
	 * @param s Your server.
	 * @param p Your plugin.
	 */
	public abstract void InitialiseScheduler(Server s, Plugin p);
	
	/**
	 * Runs a repeating task on a separate thread. (Will never stop unless you command it to).
	 * @param seconds The period to wait between executions (In seconds).
	 * @param methodContainer The class which your method is in.
	 * @param methodName The name of your method within the class to run (Case Sensitive).
	 * @param taskReference A Name given to reference to the task. Eg ("tickertask", "damagetask"). This will be much easier to maintain if you keep everything lowercase with no spaces. 
	 * You will have to track and manage the references yourself, Extras only links the reference to a hash table containing the task id.
	 */
	public abstract void scheduleAsyncRepeatingTask(int seconds, Class<?> methodContainer, String methodName, String taskReference);
	
	/**
	 * Runs a repeating task on the main thread. (Will never stop unless you command it to).
	 * @param seconds The period to wait between executions (In seconds).
	 * @param methodContainer The class which your method is in.
	 * @param methodName The name of your method within the class to run (Case Sensitive).
	 * @param taskReference A Name given to reference to the task. Eg ("tickertask", "damagetask"). This will be much easier to maintain if you keep everything lowercase with no spaces. 
	 * You will have to track and manage the references yourself, Extras only links the reference to a hash table containing the task id.
	 */
	public abstract void scheduleSyncRepeatingTask(int seconds, Class<?> methodContainer, String methodName, String taskReference);
	
	/**
	 * Runs a task on a separate thread after a delay. (Only runs once).
	 * @param delay The number of seconds delay before the task is executed.
	 * @param methodContainer The class which your method is in.
	 * @param methodName The name of your method within the class to run (Case Sensitive).
	 * @param taskReference A Name given to reference to the task. Eg ("tickertask", "damagetask"). This will be much easier to maintain if you keep everything lowercase with no spaces. 
	 * You will have to track and manage the references yourself, Extras only links the reference to a hash table containing the task id.
	 */
	public abstract void scheduleAsyncDelayedTasked(int delay, Class<?> methodContainer, String methodName, String taskReference);
	
	/**
	 * Runs a task on the main thread after a delay.  (Only runs once).
	 * @param delay The number of seconds delay before the task is executed.
	 * @param methodContainer The class which your method is in.
	 * @param methodName The name of your method within the class to run (Case Sensitive).
	 * @param taskReference A Name given to reference to the task. Eg ("tickertask", "damagetask"). This will be much easier to maintain if you keep everything lowercase with no spaces. 
	 * You will have to track and manage the references yourself, Extras only links the reference to a hash table containing the task id.
	 */
	public abstract void scheduleSyncDelayedTask(int delay, Class<?> methodContainer, String methodName, String taskReference);
	
	public abstract void killTask(String taskReference);
	
	/**
	 * Kills all scheduler tasks being handled by extras.
	 */
	public abstract void killAllExtrasTasks();
	
	/**
	 * Kills all the tasks being handled by your plugin.
	 */
	public abstract void killAllPluginTasks();
	
	/**
	 * Do not use this method. You do not need it.
	 * @return Unique Task Id. (UTID)
	 */
    protected abstract int getUniqueTaskId();
    
    /**
     * Check whether a task is active.
     * @param taskReference
     * @return A boolean value indicating whether a task is active or not.
     */
    public abstract boolean isTaskRunning(String taskReference);
	
}
