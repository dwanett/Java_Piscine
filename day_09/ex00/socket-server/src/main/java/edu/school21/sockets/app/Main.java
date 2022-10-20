package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Parameters(separators = "=")
public class Main {
	@Parameter(names={"--port"}, required = true)
	private static Integer port;

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
		Main main = new Main();
		JCommander.newBuilder()
				.addObject(main)
				.build()
				.parse(args);
		Server server = context.getBean("server", Server.class);
		server.run(port);
	}
}