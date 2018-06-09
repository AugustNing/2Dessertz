package authoring.display.popups.eventspopup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.display.controllers.EventsPopupController;
import authoring.groovy.GroovyCommandFactory;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Vbox for configuring Methods and Fields for a chosen behavior
 * @author August Ning
 *
 */
public class MFWindow extends VBox {

	private EventsPopupController epuc;
	@SuppressWarnings("unused")
	private List<GameObject> gos;
	private AuthBehavior currentBehavior;
	private GroovyCommandFactory gcf;
	
	private static final String MF = "Methods and Fields";
	private static final String NONVALID = "No Behavior or Event selected";
	private static final String METHODS = "Methods";
	private static final String NOMETHODS = "No available methods\nfor this behavior";
	private static final String FIELDS = "Fields";
	private static final String NOFIELDS = "No available fields\nfor this behavior";
	private static final int SPACING = 10;
	private static final int SIZE = 200;
	
	public MFWindow(EventsPopupController myEPUC, List<GameObject> myGos) {
		gos = myGos;
		epuc = myEPUC;
		currentBehavior = null;
		gcf = new GroovyCommandFactory();
		createVBox();
	}
	
	/**
	 * creates the VBox for the MF window
	 */
	public void createVBox() {
		this.getChildren().clear();
		this.setPadding(new Insets(SPACING));
	    this.setSpacing(SPACING);
	    this.setMinWidth(SIZE);
	    Text title = new Text(MF);
	    this.getChildren().add(title);
	    createLists();
	}
	
	private void createLists() {
		assignCurrentBehavior();
		if (currentBehavior == null || !epuc.validEvent()) {
			Text nonvalid = new Text(NONVALID);
			this.getChildren().add(nonvalid);
			return;
		}
		addMethods();
		addFields();
	}

	private void addMethods() {
		List<Method> methods = gcf.getMethods(currentBehavior);
		if (methods == null) {
			Text noMethods = new Text(NOMETHODS);
			this.getChildren().add(noMethods);
			return;
		}
		this.getChildren().add(new Text(METHODS));
		List<String> methodNames = new ArrayList<>();
		methods.forEach(m -> parseMethod(m, methodNames));
		ListView<String> methodsList = new ListView<>();
		methodsList.setMinHeight(100);
		methodsList.getItems().addAll(methodNames);
		methodsList.setOnMouseClicked(e -> methodsClicked(methodsList.getSelectionModel().getSelectedItem()));
		this.getChildren().add(methodsList);
		
	}
	private void parseMethod(Method m, List<String> list) {
		String[] holder = m.toString().split(" ");
		String methodName = holder[holder.length - 1];
		String[] name = holder[holder.length - 1].split("\\.");
		String prefix = name[0] + "." + name[1] + "." + name[2] + ".";
		methodName = methodName.replace(prefix, "");
		list.add(methodName);
	}
	private void methodsClicked(String method) {
		// This is something you can implement on epuc, since you can set text to the text plane in the groovy window
		epuc.concatenateString(method, this.getClass().getSimpleName());
	}
	
	private void addFields() {
		List<Field> fields = gcf.getFields(currentBehavior);
		if (fields == null) {
			Text noFields = new Text(NOFIELDS);
			this.getChildren().add(noFields);
			return;
		}
		this.getChildren().add(new Text(FIELDS));
		List<String> fieldNames = new ArrayList<>();
		fields.forEach(f -> parseField(f, fieldNames));
		ListView<String> fieldsList = new ListView<>();
		fieldsList.setMinHeight(100);
		fieldsList.getItems().addAll(fieldNames);
		fieldsList.setOnMouseClicked(e -> fieldsClicked(fieldsList.getSelectionModel().getSelectedItem()));
		this.getChildren().add(fieldsList);
	}
	private void parseField(Field f, List<String> list) {
		String[] holder = f.toString().split(" ");
		String fieldName = holder[holder.length - 1];
		String[] name = holder[holder.length - 1].split("\\.");
		String prefix = name[0] + "." + name[1] + "." + name[2] + ".";
		fieldName = fieldName.replace(prefix, "");
		list.add(fieldName);
	}
	private void fieldsClicked(String field) {
		// This is something you can implement on epuc, since you can set text to the text plane in the groovy window
		epuc.concatenateString(field, this.getClass().getSimpleName());
	}
	
	private void assignCurrentBehavior() {
		currentBehavior = epuc.getCurrBehavior();
	}
}
