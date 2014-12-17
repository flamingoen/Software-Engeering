package UILayer;
/**
 * martin
 */
import ProgramLayer.Developer;

public class DataTestSamples {

	private CompanyUI comUI;

	public DataTestSamples(CompanyUI companyUI) throws Exception{
		this.comUI = companyUI;
		comUI.getComApp().addDeveloper(new Developer("mot"));
		comUI.getComApp().addDeveloper(new Developer("dbg"));
		for (int i=1 ; i<=30 ; i++){
			comUI.getComApp().addDeveloper(new Developer("dev"+i));	
		}
	}
	
}