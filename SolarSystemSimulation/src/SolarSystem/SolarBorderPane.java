package SolarSystem;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SolarBorderPane extends Application {

    private VBox rtPane;						// pane in which info on system listed
    private Random rgen = new Random();			// random number generator
    private MyCanvas mc;						// canvas into which system drawn
    private SimpleSolar ourSystem;				// simple model of solar system
    private int canvasSize = 512;				// size of canvas
    private Random rand = new Random();
    private boolean animation = false;
    private boolean first = true;
    private double time;
    private double startAngle;
    private AnimationTimer animationTimer;
    private DoubleProperty animationDuration = new SimpleDoubleProperty(0L);
    

    /**
     * Function to show a message,
     *
     * @param TStr	title of message block
     * @param CStr	content of message
     */
    private void showMessage(String TStr, String CStr) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TStr);
        alert.setHeaderText(null);
        alert.setContentText(CStr);
        alert.showAndWait();
    }

    /**
     * function to show in a box ABout the programme
     */
    private void showAbout() {
        showMessage("About", "RJM's BorderPane Demonstrator");
    }

    private void showHelp() {
        showMessage("Help", "You can click the Random Earth button to do something");
    }

    /**
     * Function to set up the menu
     */
    MenuBar setMenu() {
        MenuBar menuBar = new MenuBar();		// create menu

        Menu mHelp = new Menu("Help");			// have entry for help
        // then add sub menus for About and Help
        // add the item and then the action to perform
        MenuItem mAbout = new MenuItem("About");
        mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAbout();				// show the about message
            }
        });
        mHelp.getItems().addAll(mAbout); 	// add submenus to Help
        MenuItem mSubHelp = new MenuItem("Help");
        mSubHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showHelp();				// show the about message
            }
        });
        mHelp.getItems().addAll(mSubHelp); 	// add submenus to Help
        // now add File menu, which here only has Exit
        Menu mFile = new Menu("File");
        MenuItem mExit = new MenuItem("Exit");
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);						// quit program
            }
        });
        mFile.getItems().addAll(mExit);

        menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help

        return menuBar;					// return the menu, so can be added
    }

    /**
     * show where Earth is, in pane on right
     */
    public void drawStatus() {
        rtPane.getChildren().clear();
        Label info = new Label(ourSystem.toString(mc));
        rtPane.getChildren().add(info);
    }

    /**
     * set up the mouse event handler, so when click on canvas, draw Earth there
     *
     * @param canvas
     */
    private void setMouseEvents(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ourSystem.setSystem(mc, e.getX(), e.getY());
                if (first) {
                    ourSystem.updateSystem(rand.nextInt(360));
                    first = false;
                }
                ourSystem.drawSystem(mc);
                drawStatus();
/// write here code to put sun at e.x, e.y; redraw system and update panel
            }
        });
    }

    /**
     * set up the button and return so can add to borderpane
     *
     * @return
     */
    private HBox setButtons() {
        // create button
        Button btnBottom = new Button("Random Earth");
        // now add handler
        btnBottom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ourSystem.updateSystem(rand.nextInt(360));
                ourSystem.drawSystem(mc);
                drawStatus();
            }
        });
        Button btnAnimationOn = new Button("Start");
        btnAnimationOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation = true;
                animationTimer.start();
            }
        });
        Button btnAnimationOff = new Button("Stop");
        btnAnimationOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation = false;
                animationTimer.stop();
            }
        });
        return new HBox(btnBottom, new Label(" Animation: "), btnAnimationOn, btnAnimationOff);
    }

    @Override
    public void start(Stage stagePrimary) throws Exception {
        stagePrimary.setTitle("Solar System BorderPane Demonstrator");

        BorderPane bp = new BorderPane();			// create border pane

        bp.setTop(setMenu());						// create menu, add to top

        Group root = new Group();					// create group
        Canvas canvas = new Canvas(canvasSize, canvasSize);
        // and canvas to draw in
        root.getChildren().add(canvas);			// and add canvas to group
        mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasSize, canvasSize);
        // create MyCanvas passing context on canvas onto which images put
        ourSystem = new SimpleSolar();				// create object for sun, planets, etc

        // set mouse handler
        bp.setCenter(root);							// put group in centre pane

        rtPane = new VBox();						// set vBox for listing data
        bp.setRight(rtPane);

        // put in right pane
        bp.setBottom(setButtons());					/// add button to bottom
        setMouseEvents(canvas);

        final long earthAngle = (long) ourSystem.getEarthAngle();
        long animationStart = System.nanoTime();
        animationTimer = new AnimationTimer() // create timer
        {
            @Override
            public void handle(long currentNanoTime) {
                // define handle for what do at this time

                time = (currentNanoTime - animationStart) / 1000000000.0;
                System.out.println(time);
                ourSystem.updateSystem(time);			// use time as an angle for calculating position of earth
                ourSystem.drawSystem(mc);
                drawStatus();

            }
        };

        Scene scene = new Scene(bp, canvasSize * 1.5, canvasSize * 1.2);
        // create scene so bigger than canvas, 

        stagePrimary.setScene(scene);
        stagePrimary.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
