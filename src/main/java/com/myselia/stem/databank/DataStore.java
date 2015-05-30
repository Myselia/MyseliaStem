package com.myselia.stem.databank;

public class DataStore {
	private String hashid = "";
	private int typecount;
	private EchoedValueStorage[] memory;
	
	public DataStore(String hashid, int typecount){
		this.typecount = typecount;
		memory = new EchoedValueStorage[this.typecount];
	}
	
	public String getHashID(){
		return this.hashid;
	}
	
	public int getTypecount(){
		return this.typecount;
	}
	
	/**
	 * Pushing values in the DataStore memory
	 * @param mem
	 * @param value
	 */
	public void memorypush(int mem, int value){
		memory[mem].add(value);
	}
	
	/**
	 * 
	 * @param mem
	 * @param level
	 * @param time
	 * @return specific memory value
	 */
	public double memoryfetch(int mem, int level, int time){
		double[][] store = memory[mem].getmem();
		return store[level][time];
	}
	
	/**
	 * 
	 * @param mem
	 * @return all data of that time, including echoed values
	 */
	public double[][] memoryfetch(int mem){
		return memory[mem].getmem();
	}
	
}
