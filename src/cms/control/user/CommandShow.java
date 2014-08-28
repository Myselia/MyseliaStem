package cms.control.user;

import cms.databank.OverLord;
import cms.display.bars.GraphBar;
import cms.display.graphing.DisplayType;
import cms.display.info.AddressPanel;

public class CommandShow extends AbstractCommand {
	private final static String command_signature = "show";
	private final static AddressPanel addresspanel = AddressPanel.getPanel();

	@Override
	public void action(String arg) {
		String[] parameters = super.commandParam(arg);
		switch(parameters.length){
		case 1:
			System.out.println("e>" + "Command incomplete");
			break;
		case 2:
			if(parameters[1].equals("hist"))
				histogram(DisplayType.TEMPERATURE);
			else if(parameters[1].equals("levels"))
				levels(DisplayType.TEMPERATURE);
			else if(parameters[1].equals("def"))
				define();
			else
				System.out.println("e>" + "Wrong Parameters");
			
			break;
		case 3:
			if(parameters[1].equals("hist")){
				histogram(DisplayType.TEMPERATURE, parameters[2]);
			}else if(parameters[1].equals("levels")){
				try{
					DisplayType displaytype = displayType(parameters[2]);
					levels(displaytype);
				}catch(IllegalArgumentException e){
					System.out.println("e>" + "Wrong Parameters");
				}
			}
			break;
		case 4:
			if(parameters[1].equals("hist")){
				try{
					DisplayType displaytype = displayType(parameters[3]);
					histogram(displaytype, parameters[2]);
				}catch(IllegalArgumentException e){
					System.out.println("e>" + "Wrong Parameters");
				}
			}else{
				System.out.println("e>" + "Wrong Parameters");
			}
			break;
		default:
			System.out.println("e>" + "Wrong Parameters");
			break;
		}
	}
	
	private static void histogram(DisplayType displaytype){
		int coreid = OverLord.getSelectedCore();
		if(coreid == -1){
			System.out.println("e>" + "No core currently selected");
		}else{
			if(GraphBar.isHistogram() && displaytype == GraphBar.getDisplayType()){
				System.out.println("!>" + "Already displaying Histogram");
			}else if(GraphBar.isHistogram()){
				GraphBar.setDisplayType(displaytype);
			}else{
				GraphBar.setDisplayType(displaytype);
				GraphBar.toggle();
			}
		}
	}
	
	private static void histogram(DisplayType displaytype, String parameter){
		try{
			int call = Integer.parseInt(parameter);
			addresspanel.unselectLast(addresspanel.nodeButton(call));
			addresspanel.nodeButton(call).select(true);
		}catch(NumberFormatException e){
			addresspanel.unselectLast(null);
			System.out.println("e>" + "Wrong Parameters");
		}catch(Exception e){
			System.out.println("e>" + "No such node ID");
		}
		histogram(displaytype);
		
	}
	
	private static void levels(DisplayType displaytype){
		addresspanel.unselectLast(null); /**TODO Verify if AddressBar.unselectedLast(null) creates NullPointerExceptions**/
		if(GraphBar.isHistogram()){
			GraphBar.setDisplayType(displaytype);
			GraphBar.toggle();
		}else{
			System.out.println("!>" + "Already displaying Levels");
		}
	}
	
	private static DisplayType displayType(String parameter) throws IllegalArgumentException{
		switch(parameter.toLowerCase()){
		case "temp":
			return DisplayType.TEMPERATURE;
		case "cpu":
			return DisplayType.CPU;
		case "ram":
			return DisplayType.RAM;
		case "particles":
			return DisplayType.PARTICLES;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void define() {
		System.out.println("Show the histogram of a called node.");
		System.out.println("Parameters: hist[#][temp|cpu|ram|particles] or levels [temp|cpu|ram|particles].");
		System.out.println("hist [#][temp|cpu|ram|particles] Calls node # and show its DisplayType histogram. Default is TEMPERATURE");
		System.out.println("hist 'void' show the currently selected node's TEMPERATURE histogram.");
		System.out.println("level [temp|cpu|ram|particles] deselects all nodes and display the levels of DisplayType. Default is TEMPERATURE");
		
	}
	
	@Override
	public String getCommandSignature(){
		return CommandShow.command_signature;
	}
}
