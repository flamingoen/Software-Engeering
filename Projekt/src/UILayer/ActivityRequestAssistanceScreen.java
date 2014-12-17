package UILayer;
/**
 * martin
 */
import java.io.PrintWriter;

import ProgramLayer.Activity;
import ProgramLayer.Developer;

public class ActivityRequestAssistanceScreen extends Screen {

	private Activity activity;

	public ActivityRequestAssistanceScreen(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void printMenu(PrintWriter out) {
		System.out.println("Choose developer, type list or type back: ");
	}
	
	@Override
	public boolean processInput(String input, PrintWriter out) throws Exception {
		if(input.equals("list")){
			System.out.println("# Developers in activity# ");
			for(Developer dev : companyUI.getComApp().getDevelopers()){
				System.out.println(dev.getInitials());
			}
		} else if(input.equals("back")){
			companyUI.setScreen(new ProjectMangageScreen(activity.getProject()));
		}else if (getDeveloper(input)!=null){
			if (getDeveloper(input).isAvailable(activity.getWorkPeriod())){
				companyUI.getComApp().requestAssistance(activity, getDeveloper(input));
			} else {
				wrongInput();
			}
			companyUI.setScreen(new ActivityManageScreen(activity,false));
		}
		return false;
	}
	
	private Developer getDeveloper(String input) {
		return companyUI.getComApp().getDeveloperByInitials(input);
	}

}
