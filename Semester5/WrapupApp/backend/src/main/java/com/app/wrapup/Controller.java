package com.app.wrapup;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin
public class Controller {

	public static void main(String[] args) {
		SpringApplication.run(Controller.class, args);
	}

	@PostMapping("/")
	public List<Node> wrapup(@RequestBody List<Node> nodes) {
		List<Node> convexHull = Service.giftWrapping(nodes);
		return convexHull;
	}
}
