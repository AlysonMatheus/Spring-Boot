package br.com.Alyson.Controllers;

import br.com.Alyson.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

@Autowired
private PersonServices service;
}
