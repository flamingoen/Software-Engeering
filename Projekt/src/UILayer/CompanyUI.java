package UILayer;
/**
 * martin
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import ProgramLayer.CompanyApplication;

/**
 * Strongly inspired by the LibraryUI by Hubert in the libraryApp, this class
 * should manage the screens of the UI
 * 
 * @author martin
 * 
 */
public class CompanyUI {

	private Screen screen;
	private CompanyApplication comApp = new CompanyApplication();
	private int projectIdCount;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		CompanyUI ui = new CompanyUI();
		ui.basicLoop(in, out);
	}
	
	public CompanyUI(CompanyApplication comApp) {
		setScreen(new StartScreen());
	}

	private void basicLoop(BufferedReader in, PrintWriter out)
			throws Exception {
		String selection;
		do {
			printMenu(out);
			selection = readInput(in);
		} while (!processInput(selection, out));
	}

	private boolean processInput(String input, PrintWriter out) throws Exception {
		boolean exit = getScreen().processInput(input, out);
		out.println();
		return exit;
	}

	private Screen getScreen() {
		return screen;
	}

	private String readInput(BufferedReader in) throws IOException {
		return in.readLine();
	}

	private void printMenu(PrintWriter out) {
		getScreen().printMenu(out);

	}

	public CompanyUI() {
		setScreen(new StartScreen());
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
		this.screen.setCompanyUI(this);
	}

	public CompanyApplication getComApp() {
		return comApp;
	}

	public String NewId() {
		projectIdCount++;
		return Integer.toString(projectIdCount);
	}
}
