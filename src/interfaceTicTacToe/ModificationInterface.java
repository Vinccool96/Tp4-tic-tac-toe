package interfaceTicTacToe;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ModificationInterface {

	public static Image stillTrying = new Image(
			ModificationInterface.class.getResourceAsStream("/images/StillTrying200Tr.png"));
	public static Image uRDenied = new Image(
			ModificationInterface.class.getResourceAsStream("/images/URDenied200Tr.png"));

	public static Button[][] buttons;

	public ModificationInterface(Button[][] buttons) {
		ModificationInterface.buttons = buttons;
	}

	public static void modifierImages(Button[][] imageViewTab, int[][] intTab) {
		for (int i = 0; i < intTab.length; i++) {
			for (int j = 0; j < intTab[i].length; j++) {
				if (intTab[i][j] == 1) {
					modifierImageButtonAI(imageViewTab[i][j]);
				} else if (intTab[i][j] == 2) {
					modifierImageButtonJoueur(imageViewTab[i][j]);
				} else {
					modifierImageButtonNull(imageViewTab[i][j]);
				}
			}
		}
	}

	private static void modifierImageButtonAI(Button button) {
		ImageView uRD = new ImageView(uRDenied);
		button.setGraphic(uRD);
	}

	private static void modifierImageButtonJoueur(Button button) {
		ImageView sT = new ImageView(stillTrying);
		button.setGraphic(sT);
	}

	private static void modifierImageButtonNull(Button button) {
		button.setGraphic(null);
	}

	public void changerCouleurButtons(ArrayList<Button> buttons) {
		changerCouleurButton(buttons);
	}

	private void changerCouleurButton(ArrayList<Button> buttonList) {
		for (Button button : buttonList) {

			Color startColor = Color.web("#dddddd");
			Color endColor = Color.web("#80e090");

			ObjectProperty<Color> color = new SimpleObjectProperty<Color>(startColor);
			StringBinding cssColorSpec = Bindings.createStringBinding(new Callable<String>() {
				@Override
				public String call() throws Exception {
					return String.format("-fx-body-color: rgb(%d, %d, %d);", (int) (256 * color.get().getRed()),
							(int) (256 * color.get().getGreen()), (int) (256 * color.get().getBlue()));
				}
			}, color);
			button.styleProperty().bind(cssColorSpec);
			Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(color, startColor)),
					new KeyFrame(Duration.seconds(1), new KeyValue(color, endColor)));
			timeline.setCycleCount(4);
			timeline.setAutoReverse(true);

			timeline.play();
		}
	}

}
