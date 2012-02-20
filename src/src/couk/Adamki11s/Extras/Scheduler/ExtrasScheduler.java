package couk.Adamki11s.Extras.Scheduler;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import couk.Adamki11s.Extras.Extras.Extras;
import couk.Adamki11s.Extras.Random.ExtrasRandom;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtrasScheduler extends SchedulerMethods {

	private Server s;
	private Plugin p;		
	private Method _method = null;
	private Class<?> _class = null;
	private HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	private ArrayList<Integer> idLookup = new ArrayList<Integer>();

	@Override
	public void InitialiseScheduler(Server server, Plugin plugin) {
		s = server;
		p = plugin;
	}

	@Override
	public void scheduleAsyncRepeatingTask(int seconds,
			Class<?> methodContainer, String methodName, String taskReference) {
		_method = null;
		_class = methodContainer;

		for(Method meth : methodContainer.getDeclaredMethods()){
			if(meth.getName().equals(methodName)){
				_method = meth;
			}
		}
		int taskid = getUniqueTaskId();
		taskIDs.put(taskReference, taskid);

		if(_method != null){
			taskid = s.getScheduler().scheduleAsyncRepeatingTask(p, new Runnable() {	

				public void run() {
					try {
						_method.invoke(_class.newInstance(), (Object[])null);
					} catch (IllegalArgumentException e) {
						System.out.println("[Extras][Scheduler] IllegalArguament Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.out.println("[Extras][Scheduler] IllegalAccess Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.out.println("[Extras][Scheduler] InvocationTarget Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InstantiationException e) {
						System.out.println("[Extras][Scheduler] Instantiation Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					}	

				}

			}, 20L, seconds * 20L);//First variable is delay, second is repeat period
		} else {
			System.out.println("[Extras][Scheduler] Failed to instantiate method from given class. Have you initialised the Extras scheduler? Method or class may be invalid. Caused by plugin : " + Extras.pluginName);
		}
	}

	protected int getUniqueTaskId() {
		int taskid = 0;
		boolean uniqueID = false;
		while(!uniqueID){
			boolean validUID = true;
			taskid = new ExtrasRandom().getRandomInt(1000, 1);
			for(Integer i : idLookup){
				if(i == taskid){
					validUID = false;
				}
			}
			if(validUID){
				uniqueID = true;
			}
		}
		return taskid;
	}

	@Override
	public void scheduleSyncRepeatingTask(int seconds,
			Class<?> methodContainer, String methodName, String taskReference) {
		_method = null;
		_class = methodContainer;

		for(Method meth : methodContainer.getDeclaredMethods()){
			if(meth.getName().equals(methodName)){
				_method = meth;
			}
		}
		int taskid = getUniqueTaskId();
		taskIDs.put(taskReference, taskid);

		if(_method != null){
			taskid = s.getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {	

				public void run() {
					try {
						_method.invoke(_class.newInstance(), (Object[])null);
					} catch (IllegalArgumentException e) {
						System.out.println("[Extras][Scheduler] IllegalArguament Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.out.println("[Extras][Scheduler] IllegalAccess Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.out.println("[Extras][Scheduler] InvocationTarget Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InstantiationException e) {
						System.out.println("[Extras][Scheduler] Instantiation Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					}	

				}

			}, 20L, seconds * 20L);//First variable is delay, second is repeat period
		} else {
			System.out.println("[Extras][Scheduler] Failed to instantiate method from given class. Have you initialised the Extras scheduler? Method or class may be invalid. Caused by plugin : " + Extras.pluginName);
		}

	}

	@Override
	public void scheduleAsyncDelayedTasked(int delay, Class<?> methodContainer,
			String methodName, String taskReference) {
		_method = null;
		_class = methodContainer;

		for(Method meth : methodContainer.getDeclaredMethods()){
			if(meth.getName().equals(methodName)){
				_method = meth;
			}
		}
		int taskid = getUniqueTaskId();
		taskIDs.put(taskReference, taskid);

		if(_method != null){
			taskid = s.getScheduler().scheduleAsyncDelayedTask(p, new Runnable() {	

				public void run() {
					try {
						_method.invoke(_class.newInstance(), (Object[])null);
					} catch (IllegalArgumentException e) {
						System.out.println("[Extras][Scheduler] IllegalArguament Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.out.println("[Extras][Scheduler] IllegalAccess Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.out.println("[Extras][Scheduler] InvocationTarget Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InstantiationException e) {
						System.out.println("[Extras][Scheduler] Instantiation Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					}	

				}

			}, delay * 20L);//First variable is delay, second is repeat period
		} else {
			System.out.println("[Extras][Scheduler] Failed to instantiate method from given class. Have you initialised the Extras scheduler? Method or class may be invalid. Caused by plugin : " + Extras.pluginName);
		}
	}

	@Override
	public void scheduleSyncDelayedTask(int delay, Class<?> methodContainer,
			String methodName, String taskReference) {
		_method = null;
		_class = methodContainer;

		for(Method meth : methodContainer.getDeclaredMethods()){
			if(meth.getName().equals(methodName)){
				_method = meth;
			}
		}
		int taskid = getUniqueTaskId();
		taskIDs.put(taskReference, taskid);

		if(_method != null){
			taskid = s.getScheduler().scheduleSyncDelayedTask(p, new Runnable() {	

				public void run() {
					try {
						_method.invoke(_class.newInstance(), (Object[])null);
					} catch (IllegalArgumentException e) {
						System.out.println("[Extras][Scheduler] IllegalArguament Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.out.println("[Extras][Scheduler] IllegalAccess Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.out.println("[Extras][Scheduler] InvocationTarget Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					} catch (InstantiationException e) {
						System.out.println("[Extras][Scheduler] Instantiation Exception! Caused by plugin : " + Extras.pluginName);
						e.printStackTrace();
					}	

				}

			}, delay * 20L);//First variable is delay, second is repeat period
		} else {
			System.out.println("[Extras][Scheduler] Failed to instantiate method from given class. Have you initialised the Extras scheduler? Method or class may be invalid. Caused by plugin : " + Extras.pluginName);
		}
	}

	@Override
	public void killTask(String taskReference) {
		if(taskIDs.containsKey(taskReference)){
			s.getScheduler().cancelTask(taskIDs.get(taskReference));
			idLookup.remove((Object)taskIDs.get(taskReference));
			taskIDs.remove(taskReference);
			return;
		}
		System.out.println("[Extras][Scheduler] Could not cancel task! No task reference found! Caused by plugin " + Extras.pluginName);

	}

	@Override
	public void killAllExtrasTasks() {
		for(Integer i : idLookup){
			s.getScheduler().cancelTask(i);
		}
		taskIDs.clear();
		idLookup.clear();
	}
	
	@Override
	public void killAllPluginTasks() {
		s.getScheduler().cancelTasks(p);
		taskIDs.clear();
		idLookup.clear();
	}


	@Override
	public boolean isTaskRunning(String taskReference) {
		if(taskIDs.containsKey(taskReference)){
			return true;
		} else {
			return false;
		}
	}



}
