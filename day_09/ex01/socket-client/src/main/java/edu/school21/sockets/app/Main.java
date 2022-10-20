package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Parameters(separators = "=")
public class Main {
	@Parameter(names={"--server-port"}, required = true)
	private static Integer port;

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
		Main main = new Main();
		JCommander.newBuilder()
				.addObject(main)
				.build()
				.parse(args);
		Client client = context.getBean("client", Client.class);
		client.run(port);
	}
}