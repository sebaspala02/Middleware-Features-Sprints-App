package com.redhat.bluesmile.sprintsapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.redhat.bluesmile.sprintsapp.exception.ResourceNotFoundException;
import com.redhat.bluesmile.sprintsapp.model.BackLog;
import com.redhat.bluesmile.sprintsapp.model.Noti;
import com.redhat.bluesmile.sprintsapp.model.UserModel;
import com.redhat.bluesmile.sprintsapp.repository.SprintsAppRepository;
import com.redhat.bluesmile.sprintsapp.service.SequenceGeneratorService;

@RestController
@CrossOrigin(allowedHeaders = "x-auth-token", exposedHeaders = "x-auth-token", origins = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class SprintsAppController {

//	@Autowired
//	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private SprintsAppRepository sprintsAppRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/backlog")
	public List<BackLog> getAllBacklog() {

		return sprintsAppRepository.findAll();

	}

	@GetMapping("/backlog/{backlogId}")
	public ResponseEntity<BackLog> getBacklog(@PathVariable Long backlogId) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El BackLog not found for this id :: " + backlogId));
		;
		return ResponseEntity.ok().body(backLog);
	}

	@PostMapping("/backlog")
	public BackLog createBacklog(@Validated @RequestBody BackLog backLog) {

//		JSONObject myObject = new JSONObject();
		
		backLog.setId(sequenceGeneratorService.generateSequence(BackLog.SEQUENCE_NAME));
		backLog.setCreatedAt(new Date());
//		Gson g = new Gson();
//		g.toJson(backLog.getFeatures());
//		ArrayList<Gson> feat = null;
//		feat.add(g);
//		
//		backLog.setFeatures(feat);
//		backLog.setFeatures(g.toJson(backLog.getFeatures()));
//		backLog.setFeatures(backLog.getFeatures());
		
//		storeNotification(0, backLog);

		System.out.println("  el body del request -> " + backLog);

		return sprintsAppRepository.save(backLog);

	}

//	@PostMapping("http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/")
//	public void storeNotification(@PathVariable int typeMsj, @RequestBody BackLog backLog) {
//
//		Gson g = new Gson();
//		String notification = "";
//
//		int typeUser = 0;
//		String title = backLog.getTitle();
//		if (typeMsj == 0) {
//			notification = "BackLog " + title + " has been created";
//		} else if (typeMsj == 1) {
//			notification = "BackLog " + title + " has been modified";
//		} else {
//			notification = "BackLog " + title + " has been deleted";
//		}
//		Noti noti = new Noti(typeUser, title, notification);
//
//		System.out.println(g.toJson(noti));
//
//		String url = "http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/";
//		Map<String, String> params = new HashMap<>(); // put here your params.
//
//		RestTemplate template = new RestTemplate();
//		template.postForLocation(url, noti, params);
//
//	}

	@PutMapping("/backlog/{backlogId}")
	public ResponseEntity<BackLog> updateBacklog(@PathVariable Long backlogId,
			@Validated @RequestBody BackLog backLogDetails) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El Backlog not found for this id :: " + backlogId));

		backLog.setOperation(backLogDetails.getOperation());
		backLog.setTitle(backLogDetails.getTitle());
		backLog.setDescription(backLogDetails.getDescription());
		backLog.setProject(backLogDetails.getProject());
		backLog.setFeatures(backLogDetails.getFeatures());
		backLog.setCreatedBy(backLogDetails.getCreatedBy());
		backLog.setCreatedAt(new Date());
		backLog.setStatus(backLogDetails.getStatus());

		final BackLog updatedBackLog = sprintsAppRepository.save(backLog);
//		storeNotification(1, backLog);
		return ResponseEntity.ok(updatedBackLog);
	}

}