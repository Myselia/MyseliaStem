package com.mycelia.stem.communication.topology;

import java.util.HashMap;
import java.util.LinkedList;

public class TopologyUnit {
	
	//This topology's commander
	private ComponentPointer commander = null;
	
	//Siblings for the stem
	private HashMap<ComponentPointer, TopologyUnit> siblings = new HashMap<ComponentPointer, TopologyUnit>();
	
	//Database list
	private LinkedList<ComponentPointer> databases = new LinkedList<ComponentPointer>();
	
	//Application copies
	private LinkedList<ComponentPointer> applications = new LinkedList<ComponentPointer>();
	
	//Daemons and their applications
	private HashMap<ComponentPointer, String> daemonMap = new HashMap<ComponentPointer, String>();
	
	
	
	

	public ComponentPointer getCommander() {
		return commander;
	}

	public void setCommander(ComponentPointer commander) {
		this.commander = commander;
	}

	
	
	public HashMap<ComponentPointer, TopologyUnit> getSiblings() {
		return siblings;
	}

	public void setSiblings(HashMap<ComponentPointer, TopologyUnit> siblings) {
		this.siblings = siblings;
	}

	
	
	public LinkedList<ComponentPointer> getDatabases() {
		return databases;
	}

	public void setDatabases(LinkedList<ComponentPointer> databases) {
		this.databases = databases;
	}

	
	
	public LinkedList<ComponentPointer> getApplications() {
		return applications;
	}

	public void setApplications(LinkedList<ComponentPointer> applications) {
		this.applications = applications;
	}

	
	
	public HashMap<ComponentPointer, String> getDaemonMap() {
		return daemonMap;
	}

	public void setDaemonMap(HashMap<ComponentPointer, String> daemonMap) {
		this.daemonMap = daemonMap;
	}

}
