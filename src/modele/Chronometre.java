package modele;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class Chronometre {

	private StringProperty chronometreTxt;
	private BooleanProperty stopped;

	private Timer timer = new Timer(true);
	private int secondes = 0;
	private int minutes = 0;
	private int heures = 0;

	public Chronometre(BooleanProperty booleanProperty, Label lblChronometre) {
		chronometreTxt = new SimpleStringProperty();
		stopped = new SimpleBooleanProperty();
		lblChronometre.textProperty().bind(chronometreTxt);
		stopped.bind(booleanProperty);
		timer.schedule(new ChronometreTimerTask(), 0, 1000);

		stopped.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					timer.cancel();
				}
			}
		});
	}

	public String getSecondesString() {
		double time = secondes;
		String str = time + "";
		return (str.charAt(1) == '.' ? "0" + str.charAt(0) : "" + (str.charAt(0)) + (str.charAt(1)));
	}

	public String getMinutesString() {
		double time = minutes;
		String str = time + "";
		return (str.charAt(1) == '.' ? "0" + str.charAt(0) : "" + (str.charAt(0)) + (str.charAt(1)));
	}

	public String getHeuresString() {
		double time = heures;
		String str = time + "";
		return (str.charAt(1) == '.' ? "0" + str.charAt(0) : "" + (str.charAt(0)) + (str.charAt(1)));
	}

	public void restart() {
		timer.cancel();
		secondes = 0;
		minutes = 0;
		heures = 0;
		timer = new Timer(true);
		timer.schedule(new ChronometreTimerTask(), 0, 1000);
	}

	public class ChronometreTimerTask extends TimerTask {
		@Override
		public void run() {
			String str = getHeuresString() + ":" + getMinutesString() + ":" + getSecondesString();
			Platform.runLater(() -> {
				chronometreTxt.set(str);
			});
			if (secondes > 59) {
				if (minutes > 59) {
					heures++;
					minutes = 0;
					secondes = 0;
				} else {
					minutes++;
					secondes = 0;
				}
			} else {
				secondes++;
			}
		}
	}

}
