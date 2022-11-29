package de.rnschk;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class H2_Server {
	private static final Logger log = LoggerFactory.getLogger(H2_Server.class);

	@Value("${server.port}")
	private String serverPort;

	@Value("${spring.h2.console.path}")
	private String path;

	@Value("${de.rnschk.h2server.jdbcport:9092}")
	private String port;

	@Value("${de.rnschk.h2server.dbname:rns}")
	private String dbname;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		var server = Server.createTcpServer("-tcp", "-tcpPort", port);
		log.info("--> http://localhost:{}{}", serverPort, path);
		return server;
	}

	@Bean
    public DataSource dataSource() {
	    return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(dbname)
				.build();
    }

	public static void main(final String[] args) {
		SpringApplication.run(H2_Server.class, args);
	}
}
