package com.example.testingrestdocs;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@PostMapping("/message")
	public void createMessage(@RequestBody @Valid Message message) {
		System.out.print("Received: " + message);
	}
}
