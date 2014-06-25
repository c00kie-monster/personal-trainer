package com.kikolski.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.kikolski.model.MainModel;
import com.kikolski.model.service.ExerciseService;
import com.kikolski.model.service.WorkoutService;

public class MainApplication extends Application{
	
	public static void main(String[] params) {
		ContextWrapper.initialize();
		
		launch(params);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		inititalizeModel();
		showView(stage);
	}
		
	private void inititalizeModel() {
		MainModel model = ContextWrapper.getBean(MainModel.class);
		WorkoutService workoutService = ContextWrapper.getBean(WorkoutService.class);
		ExerciseService exerciseService = ContextWrapper.getBean(ExerciseService.class);
		
		if (model != null) {
			model.setExercises(exerciseService.getAll());
			model.setWorkouts(workoutService.getAll());
		}
	}
	
	private void showView(Stage stage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("../views/MainApplication.fxml"));
		Scene scene = new Scene(root, 600, 450);
		stage.setTitle("Training Assistant");
        stage.setScene(scene);
        stage.show();
	}
}
