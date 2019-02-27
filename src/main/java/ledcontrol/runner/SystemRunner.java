package ledcontrol.runner;

import static java.awt.Color.BLACK;
import static java.awt.Color.GREEN;
import static java.awt.Color.WHITE;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import ledcontrol.TheSystem;
import ledcontrol.connection.SerialConnection;
import ledcontrol.panel.MixPanel;
import ledcontrol.scene.FlashScene;
import ledcontrol.scene.GoalScene;

public class SystemRunner {

	public static void main(String[] args) throws IOException, NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, InterruptedException, MqttSecurityException, MqttException {

		SECONDS.sleep(2);

		SerialConnection connection = new SerialConnection("/dev/ttyUSB4", 230400);
		int ledCount = 120;

		MixPanel panel = new MixPanel(ledCount, 1);
		panel.fill(BLACK);

		GoalScene goalScene = new GoalScene(panel.createSubPanel(), GREEN);
		FlashScene flashScene = new FlashScene(panel.createSubPanel());
		try (TheSystem system = new TheSystem("localhost", 1883, panel, connection.getOutputStream()) {
			@Override
			protected void received(String topic, String payload) {
				if (topic.equals("goal")) {
					if (payload.equals("team1")) {
						goalScene.incrementScore();
					}
				}
				if (topic.equals("flash")) {
					flashScene.flash(WHITE, SECONDS, 1);
				}
			}
		}) {
			Object o = new Object();
			synchronized (o) {
				o.wait();
			}
		}

	}

}
