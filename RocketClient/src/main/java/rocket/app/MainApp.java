package rocket.app;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netgame.common.Client;

import rocket.app.view.MortgageController;
import rocketData.LoanRequest;
import rocketServer.RocketHub;

public class MainApp extends Application {

	private Stage primaryStage;
	private RocketHub rHub = null;
	private RocketClient rClient = null;
	private MortgageController rController; 
	private final int PORT = 9004;
	private final String COMPUTERNAME = "localhost";
	
	public static void main(String[] args) {
		       
		launch(args);
	}

	@Override
	public void init() throws Exception {
		StartHubAndClient();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// START is executed by the Application framework after INIT
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, 1300, 500);

		this.primaryStage = primaryStage;
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		
		this.primaryStage.setTitle("Rocket");
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		
		showRocketMenu();
		
	}
	
	public void StartHubAndClient()
	{
		//	Start the Hub and Client
		
		try {
			rHub = new RocketHub(PORT);
		} catch (Exception e) {
			System.out.println("Error: Can't listen on port " + PORT);
			e.printStackTrace();
		}
		
		try {
			rClient = new RocketClient(COMPUTERNAME, PORT);
		} catch (IOException e) {
			System.out.println("Can't Start Client");
			e.printStackTrace();
		}	
	}
	public void showRocketMenu() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Mortgage.fxml"));
			AnchorPane RocketMain = (AnchorPane) loader.load();

			Scene scene = new Scene(RocketMain);

			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			rController = loader.getController();
			rController.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void EndPoker() {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	@Override
	public void stop() throws Exception {
		rClient.disconnect();
		rHub.shutDownHub();
	}

	public void messageSend(final Object message) {
		rClient.messageSend(message);
	}

	private class RocketClient extends Client {

		public RocketClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
		}

		protected void messageSend(Object message) {
			resetOutput();
			super.send(message);
		}

		@Override
		protected void messageReceived(final Object message) {
			Platform.runLater(() -> {
				if (message instanceof LoanRequest) {
					LoanRequest lq = (LoanRequest)message;
					rController.HandleLoanRequestDetails(lq);
				} 
				else if (message instanceof Object) {
				}
			});
		}

		@Override
		protected void serverShutdown(String message) {

			Platform.runLater(() -> {
				Platform.exit();
				System.exit(0);
			});
		}

	}
}
