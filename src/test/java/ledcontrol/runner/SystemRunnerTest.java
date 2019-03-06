package ledcontrol.runner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class SystemRunnerTest {

	@Rule
	public EnvironmentVariables envvars = new EnvironmentVariables();

	@Test
	public void canReadEnvVars() throws MqttSecurityException, IOException, InterruptedException, MqttException {
		SystemRunner systemRunner = new SystemRunner();
		envvars //
				.set("BAUDRATE", "1")//
				.set("LEDS", "2")//
				.set("MQTTHOST", "someMqttHost")//
				.set("MQTTPORT", "3") //
				.set("TTY", "someTTY");
		assertThat(systemRunner.parseArgs(), is(true));
		assertThat(systemRunner.baudrate, is(1));
		assertThat(systemRunner.leds, is(2));
		assertThat(systemRunner.mqttHost, is("someMqttHost"));
		assertThat(systemRunner.mqttPort, is(3));
		assertThat(systemRunner.tty, is("someTTY"));

	}

}
