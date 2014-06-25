package com.kikolski.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import com.kikolski.application.ContextWrapper;
import com.kikolski.model.MainModel;
import com.kikolski.model.exception.DAOException;
import com.kikolski.model.exception.ValidationException;
import com.kikolski.model.persistence.Exercise;
import com.kikolski.model.persistence.Workout;
import com.kikolski.model.service.ExerciseService;
import com.kikolski.model.service.WorkoutService;
import com.kikolski.model.validation.error.UserError;

public class MainController implements Initializable{
	@FXML private TextField newExerciseName, selectedExerciseBodyPart, newExerciseBodyPart, workoutName;
	@FXML private TextArea newExerciseDesc, workoutDesc, selectedExerciseDesc;
	@FXML private ListView<Exercise> exercisesList, exercisesListForWorkout;
	@FXML private ComboBox<String> workoutDays;
	@FXML private TreeView<Object> planTree;
	@FXML private TreeItem<Object> root;
	@FXML private Label planWorkoutDesc;
	@FXML private Stage stage;
	
	private WorkoutService workoutService = ContextWrapper.getBean(WorkoutService.class);
	private ExerciseService service = ContextWrapper.getBean(ExerciseService.class);
	private List<String> trainingDays = (List<String>) ContextWrapper.getBean("days");
	private MainModel model = ContextWrapper.getBean(MainModel.class);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		workoutDays.setItems(FXCollections.observableArrayList(trainingDays));
		exercisesList.setItems(FXCollections.observableArrayList(model.getExercises()));
		
		exercisesListForWorkout.setItems(FXCollections.observableArrayList(model.getExercises()));
		exercisesListForWorkout.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		planTree.getSelectionModel().selectedItemProperty().addListener(new WorkoutSelectionChangedListener());
		exercisesList.getSelectionModel().selectedItemProperty().addListener(new ExerciseSelectionChangedListener());
		createDaysTree();
	}
		
	@FXML
	protected void handleCreateExerciseAction(ActionEvent event) {
		Exercise exercise;

		try {
			exercise = ContextWrapper.getBean(Exercise.class);
			exercise.setDescription(newExerciseDesc.getText());
			exercise.setName(newExerciseName.getText());
			exercise.setBodyPart(newExerciseBodyPart.getText());
			service.add(exercise);
			model.getExercises().add(exercise);
			exercisesList.getItems().add(exercise);
			exercisesListForWorkout.getItems().add(exercise);
			clearNewExerciseFields();
		} catch (ValidationException exception) {
			showErrorMessage(exception.getErrorsList());
		} catch (DAOException dao) {
			showErrorMessage("Nie można dodać tego ćwiczenia do bazy");
		} 		
	
	}
		
	@FXML private void handleDeleteExerciseAction(ActionEvent event) {
		Exercise exercise = exercisesList.getSelectionModel().getSelectedItem();
		try{
			service.delete(exercise);
			model.getExercises().remove(exercise);
			exercisesList.getItems().remove(exercise);
			exercisesListForWorkout.getItems().remove(exercise);
		} catch (Exception e) {
			showErrorMessage("Nie można usunąć tego ćwiczenia - jest uzywane w treningu");
		}
	}
	
	@FXML
	private void handleEditExerciseAction(ActionEvent event) {
		Exercise exercise = exercisesList.getSelectionModel().getSelectedItem();
		
		if (exercise == null) {
			showErrorMessage("Nie wybrano ćwiczenia.");
			return;
		}
		
		String currentDesc = exercise.getDescription();
		String currentBodyPart = exercise.getBodyPart();

		try {
			exercise.setBodyPart(selectedExerciseBodyPart.getText());
			exercise.setDescription(selectedExerciseDesc.getText());
			service.update(exercise);
		} catch (ValidationException exception) {
			showErrorMessage(exception.getErrorsList());
			exercise.setBodyPart(currentBodyPart);
			exercise.setDescription(currentDesc);
		} catch (DAOException dao) {
			showErrorMessage("Nie można edytować tego ćwiczenia");
		}
	}
	
	@FXML private void handleCreateWorkoutAction(ActionEvent event) {
		List<Exercise> selectedExercises = exercisesListForWorkout.getSelectionModel().getSelectedItems();
		Workout workout = ContextWrapper.getBean(Workout.class);
		workout.setExercises(selectedExercises);
		workout.setDay(workoutDays.getSelectionModel().getSelectedItem());
		workout.setName(workoutName.getText());
		model.getWorkouts().add(workout);
		try{
			workoutService.add(workout);
			clearWorkoutFields();
			createDaysTree();
		} catch (ValidationException e) {
			showErrorMessage(e.getErrorsList());
		} catch (DAOException e){
			e.printStackTrace();
		}
	}
		
	private void clearWorkoutFields() {
		workoutDays.getSelectionModel().clearSelection();
		workoutDesc.clear();
		workoutName.clear();
	}
	
	private void clearNewExerciseFields() {
		newExerciseBodyPart.clear();
		newExerciseDesc.clear();
		newExerciseName.clear();
	}
	
	private class ExerciseSelectionChangedListener implements ChangeListener<Exercise> {
		@Override
		public void changed(ObservableValue<? extends Exercise> observable, Exercise oldValue, Exercise newValue) {
			if (newValue != null) {
				selectedExerciseBodyPart.setText(newValue.getBodyPart());
				selectedExerciseDesc.setText(newValue.getDescription());
			}
		}
	}

	@FXML private void deleteFromCurrentPlan(ActionEvent event) {
		TreeItem<Object> selectedObject = (TreeItem<Object>) planTree.getSelectionModel().getSelectedItem();
		
		if (selectedObject == null) {
			showErrorMessage("Musisz wybrać jeden plan ćwiczeń.");
		}
		
		if (selectedObject.getValue() instanceof Workout) {
			Workout workout = (Workout) selectedObject.getValue();
			selectedObject.getParent().getChildren().remove(selectedObject);
			model.getWorkouts().remove(workout);
			workoutService.delete(workout);
		}
	}
	
	private void createDaysTree() {
		root = new TreeItem<Object>("Plan ćwiczeń");
		List<Workout> workouts = model.getWorkouts();
		for (String day : trainingDays){
			TreeItem<Object> node = new TreeItem<Object>(day);
			for (Workout workout : workouts) {
				if (workout.getDay().equals(day)) {
					TreeItem<Object> workoutNode = new TreeItem<Object>(workout);
					node.getChildren().add(workoutNode);
				}
			}
			root.getChildren().add(node);
		}
		planTree.setRoot(root);
		root.setExpanded(true);
	}
	
	private void showErrorMessage(List<UserError> errors) {
		StringBuilder builder = new StringBuilder();
		for (UserError error : errors)
			builder.append(error.getMessage()).append("\n");
		Dialogs.create().message(builder.toString()).showError();
	}
	
	private void showErrorMessage(String message) {
		Dialogs.create().message(message).showError();
	}
	
	private class WorkoutSelectionChangedListener implements ChangeListener<TreeItem> {
		@Override
		public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue, TreeItem newValue) {
			if (newValue != null) {
				if (newValue.getValue() instanceof Workout) {
					Workout selectedWorkout = (Workout) newValue.getValue();
					StringBuilder desc = new StringBuilder();
					for (Exercise exercise : selectedWorkout.getExercises()) {
						desc.append(exercise.getName()).append(" [").append(exercise.getBodyPart())
						.append("]:\n").append(exercise.getDescription()).append("\n\n");
					}
					planWorkoutDesc.setText(desc.toString());
				}
			}
		}
	}
}