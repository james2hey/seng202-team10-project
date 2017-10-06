package dataHandler;

import GUIControllers.ProgressPopupController;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;

import static org.junit.Assert.*;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.sql.ResultSet;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by jes143 on 6/10/17.
 */
@RunWith(JfxRunner.class)
public class CSVImporterTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static SQLiteDB db;

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
    }

    @After
    public void tearDown() throws Exception {
        db.executeUpdateSQL("DROP TABLE route_information");
        db.executeUpdateSQL("DROP TABLE retailer");
        db.executeUpdateSQL("DROP TABLE wifi_location");
    }

    @Before
    public void init() throws Exception {
    }

    @Test
    public void call() throws Exception {

    }

    @Test
    public void result() throws Exception {

    }

}