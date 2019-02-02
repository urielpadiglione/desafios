package com.urielpadiglione.stringparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.urielpadiglione.stringparser.component.DataComponent;
import com.urielpadiglione.stringparser.service.StringParseService;

@SpringBootApplication
public class DesafiosApplication implements CommandLineRunner{
	
	@Autowired
	DataComponent dataComponent; //o component é onde decidimos quais variaveis serão aceitas na hora de executar
	
	@Autowired
	StringParseService stringParseService;

	public static void main(String[] args) {
		SpringApplication.run(DesafiosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		stringParseService.parseString(dataComponent.getTexto(),dataComponent.getLength());
		
	}

}

