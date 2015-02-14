package cms.control.user;

import cms.databank.OverLord;
import cms.display.graphing.GraphingHistogram;
import cms.helpers.TestThread;
import cms.monitoring.LogSystem;

public class CommandTest extends AbstractCommand {
	
	private final static String command_signature = "test";
	private static Thread thread;

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		if (parameters.length > 1) {
			switch (parameters[1]) {
			case "data":
				testdata();
				break;
			case "def":
				define();
				break;
			case "console":
				testconsole();
				break;
			case "log":
				testlog();
				break;
			case "num":
				testnum(parameters);
				break;
			case "start":
				teststart(parameters);
				break;
			case "stop":
				testloop(new TestThread(), -1, false);
				break;
			default:
				System.out.println("e>" + "Wrong Parameters");
			}
		} else {
			System.out.println("e>" + "Command Incomplete");
		}

	}

	@Override
	public void define() {
		System.out.println("Parameters: data|def|console|log|continuous|stop");
		System.out.println("data tests data on the levels/graph once.");
		System.out.println("console and log feed 40 lines to console and log respectively.");
		System.out.println("continuous opens a thread sending data every second");
		System.out.println("stop closes the thread sending data every second, if any");
		System.out.println("Tests specific parts of the program");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandTest.command_signature;
	}
	
	public static void testdata(){
		LogSystem.log(true, false, "Testing new data");
		OverLord.newData();
	}
	
	private void testconsole(){
		System.out.println("Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			System.out.println("Line test for caret: " + (i+1));
		}
	}
	
	private void testlog(){
		LogSystem.log(true, false, "Testing scroll and caret");
		for(int i = 0 ; i < 40 ; i++){
			LogSystem.log(true, false, "Line test for caret: " + (i+1));
		}
	}
	
	private void testnum(String[] args){
		try{
			switch(args.length){
			case 4:
				GraphingHistogram.number_offset = Integer.parseInt(args[3]);
			case 3:
				GraphingHistogram.line_offset = Integer.parseInt(args[2]);
				break;
			default:
				System.out.println("Fuck off.");
			}
		}catch(Exception e){}
	}
	
	public static void teststart(String[] args){
		switch(args.length){
		case 2:
			testloop(new TestThread(), -1, true);
			break;
		case 3:
			int loops = 0;
			try{
				loops = Integer.parseInt(args[2]);
			}catch(Exception e){
				return;
			}
			testloop(new TestThread(), loops, true);
		default:
			System.out.println("e>" + "Wrong Parameters");
		}
	}
	
	public static void testloop(TestThread instance, int loops, boolean start) {
		if(start){
			instance.setLoops(loops);
			thread = new Thread(instance);
			thread.start();
		}else if(thread != null){
			try{
				thread.interrupt();
			}catch(Exception e){	
				e.printStackTrace();
			}
		}else{
			System.out.println("e>" + "Test thread is not running");
		}
	}

}
