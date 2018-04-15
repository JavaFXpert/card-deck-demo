package com.javafxpert.carddeckdemo.deck.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@ConfigurationProperties("poker.server")
public class PokerServerProperties {

	private String host;
	private String port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBaseURI() {
		return UriComponentsBuilder.fromHttpUrl(host)
		                           .port(port)
		                           .build()
		                           .toUriString();

	}
}
