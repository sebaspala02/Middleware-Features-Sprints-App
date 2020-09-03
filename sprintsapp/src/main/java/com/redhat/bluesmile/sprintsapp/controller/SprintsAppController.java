package com.redhat.bluesmile.sprintsapp.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.redhat.bluesmile.sprintsapp.exception.ResourceNotFoundException;
import com.redhat.bluesmile.sprintsapp.model.BackLog;
import com.redhat.bluesmile.sprintsapp.model.Features;
import com.redhat.bluesmile.sprintsapp.model.Issue;
import com.redhat.bluesmile.sprintsapp.model.Noti;
//import com.redhat.bluesmile.sprintsapp.repository.SprintsAppFeatureRepository;
import com.redhat.bluesmile.sprintsapp.repository.SprintsAppRepository;
import com.redhat.bluesmile.sprintsapp.service.SequenceGeneratorService;

@RestController
@CrossOrigin(allowedHeaders = "x-auth-token", exposedHeaders = "x-auth-token", origins = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/api")
public class SprintsAppController {

	Features featuresNULL = new Features(0, "", "", null, null, null, 0, null, null, null, 0);
	BackLog backLogNULL = new BackLog(0, "", null, null, null, null, null, null, 0, 0);
	Issue issueNULL = new Issue(0, "", null, null, null, null, 0);

	@Autowired
	private SprintsAppRepository sprintsAppRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/backlog")
	public List<BackLog> getAllBacklog() {

		List<BackLog> backLog = sprintsAppRepository.findByStatus();

		List<BackLog> listNewBacklog = new ArrayList<BackLog>();
		for (int i = 0; i < backLog.size(); i++) {
			BackLog back = backLog.get(i);
			Features[] features = back.getFeatures();
			Features[] newFeature = new Features[features.length];
			for (int j = 0; j < features.length; j++) {
				int featureStatus = features[j].getStatus();
				if (featureStatus != 3) {
					newFeature[j] = features[j];
				}
			}
			back.setFeatures(newFeature);
			listNewBacklog.add(back);
		}
		return listNewBacklog;

//		return sprintsAppRepository.findByStatus(3);

	}

	@GetMapping("/backlog/{backlogId}")
	public ResponseEntity<BackLog> getBacklog(@PathVariable Long backlogId) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El BackLog not found for this id :: " + backlogId));

		Features[] features = backLog.getFeatures();
		Features[] newFeature = new Features[features.length];
		for (int i = 0; i < features.length; i++) {
			int featureStatus = features[i].getStatus();
			if (featureStatus != 3) {
				newFeature[i] = features[i];
			}
		}
		backLog.setFeatures(newFeature);

		return ResponseEntity.ok().body(backLog);
	}

	@SuppressWarnings("finally")
	@PostMapping("/backlog")
	public BackLog createBacklog(@Validated @RequestBody BackLog backLog) {

		backLog.setId(sequenceGeneratorService.generateSequence(BackLog.SEQUENCE_NAME));
		backLog.setCreatedAt(new Date());

		System.out.println("  el body del request -> " + backLog);

		try {
			storeNotification(0, backLog, featuresNULL, issueNULL);
		} finally {
			// TODO: handle finally clause
			return sprintsAppRepository.save(backLog);
		}
	}

	@PostMapping("http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/")
	public void storeNotification(@PathVariable int typeMsj, @RequestBody BackLog backlogDetails,
			@RequestBody Features featuresDetails, @RequestBody Issue issueDetails) {

		Gson g = new Gson();
		String notification = "";

		int typeBacklog = 2;
		int typeFeatures = 3;
		String name = featuresDetails.getName();
		String title = backlogDetails.getTitle();
		String titleIssue = issueDetails.getTitle();

		if (featuresDetails.getName().equals("")) {

			if (typeMsj == 0) {
				notification = "Backlog " + title + " has been created";
			} else if (typeMsj == 1) {
				notification = "Backlog " + title + " has been modified";
			} else {
				notification = "Backlog " + title + " has been deleted";
			}
			Noti noti = new Noti(typeBacklog, title, notification);

			System.out.println(g.toJson(noti));

			String url = "http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/";
			Map<String, String> params = new HashMap<>(); // put here your params.

			RestTemplate template = new RestTemplate();
			template.postForLocation(url, noti, params);

		} else if (backlogDetails.getTitle().equals("")) {

			if (typeMsj == 0) {
				notification = "Features " + name + " has been created";
			} else if (typeMsj == 1) {
				notification = "Features " + name + " has been modified";
			} else {
				notification = "Features " + name + " has been deleted";
			}
			Noti noti = new Noti(typeFeatures, name, notification);

			System.out.println(g.toJson(noti));

			String url = "http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/";
			Map<String, String> params = new HashMap<>(); // put here your params.

			RestTemplate template = new RestTemplate();
			template.postForLocation(url, noti, params);

		} else if (backlogDetails.getTitle().equals("") && featuresDetails.getName().equals("")) {

			if (typeMsj == 0) {
				notification = "Issue " + titleIssue + " has been created";
			} else if (typeMsj == 1) {
				notification = "Issue " + titleIssue + " has been modified";
			} else {
				notification = "Issue " + titleIssue + " has been deleted";
			}
			Noti noti = new Noti(typeFeatures, titleIssue, notification);

			System.out.println(g.toJson(noti));

			String url = "http://notifications-app-notifications-app.apps.na311.openshift.opentlc.com/api/notifications/";
			Map<String, String> params = new HashMap<>(); // put here your params.

			RestTemplate template = new RestTemplate();
			template.postForLocation(url, noti, params);
		}

	}

	@SuppressWarnings("finally")
	@PutMapping("/backlog/{backlogId}")
	public ResponseEntity<BackLog> updateBacklog(@PathVariable Long backlogId,
			@Validated @RequestBody BackLog backLogDetails) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El Backlog not found for this id :: " + backlogId));

		backLog.setOperation(backLogDetails.getOperation());
		backLog.setTitle(backLogDetails.getTitle());
		backLog.setDescription(backLogDetails.getDescription());
		backLog.setProject(backLogDetails.getProject());
		backLog.setClient(backLogDetails.getClient());
		backLog.setFeatures(backLogDetails.getFeatures());
		backLog.setCreatedBy(backLogDetails.getCreatedBy());
		backLog.setCreatedAt(new Date());
		backLog.setStatus(backLogDetails.getStatus());

		try {
			if (backLogDetails.getOperation() == 0) {
				storeNotification(1, backLog, featuresNULL, issueNULL);
			} else if (backLogDetails.getOperation() == 1) {
				storeNotification(2, backLog, featuresNULL, issueNULL);
			}
		} finally {
			// TODO: handle finally clause
			final BackLog updatedBackLog = sprintsAppRepository.save(backLog);
			return ResponseEntity.ok(updatedBackLog);
		}

	}

//	---------------------------------------------------------FEATURES---------------------------------------------------------------

//	@Autowired
//	SprintsAppFeatureRepository sprintsAppFeatureRepository;

	@GetMapping("/features")
	public List<Features> getAllFeatures() {
//		Gson g = new Gson();
		List<BackLog> backLog = sprintsAppRepository.findAll();
		List<Features> listFeatures = new ArrayList<Features>();
		for (int i = 0; i < backLog.size(); i++) {
			BackLog back = backLog.get(i);
			Features[] backlogFeatures = back.getFeatures();
			for (int j = 0; j < backlogFeatures.length; j++) {
				int status = backlogFeatures[j].getStatus();
				if (status != 3) {
					listFeatures.add(backlogFeatures[j]);
				}
			}
//			System.out.println("FEATURES: " + g.toJson(listFeatures));
			if (i == backLog.size() - 1) {
				return listFeatures;
			}
		}
		return null;
	}

	@GetMapping("/feature/{featuresId}")
	public ResponseEntity<Features> getFeature(@PathVariable Long featuresId) throws ResourceNotFoundException {

		List<Features> listFeatures = getAllFeatures();

		for (Features backlogFeature : listFeatures) {

			Long idFeature = backlogFeature.getId();

			boolean condition = false;

			condition = idFeature.equals(featuresId);

			while (condition) {
				return ResponseEntity.ok().body(backlogFeature);
			}

		}

		return ResponseEntity.notFound().build();

	}

	@SuppressWarnings("finally")
	@PostMapping("/feature/{backlogId}")
	public BackLog createFeature(@Validated @RequestBody Features feature, @PathVariable Long backlogId)
			throws ResourceNotFoundException {

		feature.setId(sequenceGeneratorService.generateSequence(Features.SEQUENCE_NAME));
		feature.setCreatedAt(new Date());
		feature.setIdBacklog(backlogId);

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El Feature not found for this id :: " + backlogId));

		Features[] backlogFeatures = backLog.getFeatures();

		Features[] features = new Features[backlogFeatures.length + 1];

		for (int i = 0; i < backlogFeatures.length; i++) {

			features[i] = backlogFeatures[i];

		}

		features[features.length - 1] = feature;

		backLog.setFeatures(features);
		try {
			storeNotification(0, backLogNULL, feature, issueNULL);
		} finally {
			// TODO: handle finally clause
			return sprintsAppRepository.save(backLog);
		}

	}

	@SuppressWarnings("finally")
	@PutMapping("/feature/{backlogId}/{featuresId}")
	public ResponseEntity<Features> updateFeature(@PathVariable Long featuresId, @PathVariable Long backlogId,
			@Validated @RequestBody Features featuresDetails) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El Feature not found for this id :: " + backlogId));

		Features[] backlogFeatures = backLog.getFeatures();

		for (Features backlogFeature : backlogFeatures) {

			Long idFeature = backlogFeature.getId();

			boolean condition = false;

			condition = idFeature.equals(featuresId);

			while (condition) {

				backlogFeature.setName(featuresDetails.getName());
				backlogFeature.setDescription(featuresDetails.getDescription());
				backlogFeature.setStartDate(featuresDetails.getStartDate());
				backlogFeature.setIdBacklog(featuresDetails.getIdBacklog());
				backlogFeature.setEndDate(featuresDetails.getEndDate());
				backlogFeature.setDeveloper(featuresDetails.getDeveloper());
				backlogFeature.setIssues(featuresDetails.getIssues());
				backlogFeature.setCreatedBy(featuresDetails.getCreatedBy());
				backlogFeature.setCreatedAt(featuresDetails.getCreatedAt());
				backlogFeature.setStatus(featuresDetails.getStatus());

				sprintsAppRepository.save(backLog);

				try {
					if (featuresDetails.getStatus() == 3) {
						storeNotification(2, backLogNULL, featuresDetails, issueNULL);
					} else if (true) {
						storeNotification(1, backLogNULL, featuresDetails, issueNULL);
					}
				} finally {
					// TODO: handle finally clause
					return ResponseEntity.ok().body(backlogFeature);
				}
			}

		}

		return null;
	}

//	---------------------------------------------------------ISSUES---------------------------------------------------------------

	@GetMapping("/issues")
	public List<Issue> getAllIssues() {
//		Gson g = new Gson();
		List<Features> listFeatures = getAllFeatures();
		List<Issue> listIssue = new ArrayList<Issue>();
		for (int i = 0; i < listFeatures.size(); i++) {
			Features feature = listFeatures.get(i);
			Issue[] featuresIssues = feature.getIssues();
			for (int j = 0; j < featuresIssues.length; j++) {
				int status = featuresIssues[j].getEstado();
				if (status != 3) {
					listIssue.add(featuresIssues[j]);
				}
			}
//			System.out.println("FEATURES: " + g.toJson(listFeatures));
			if (i == listFeatures.size() - 1) {
				return listIssue;
			}
		}
		return null;
	}

	@GetMapping("/issue/{issueId}")
	public ResponseEntity<Issue> getIssue(@PathVariable Long issueId) throws ResourceNotFoundException {

		List<Features> listFeatures = getAllFeatures();
		for (int i = 0; i < listFeatures.size(); i++) {
			Features feature = listFeatures.get(i);
			Issue[] featuresIssues = feature.getIssues();
			for (Issue featuresIssue : featuresIssues) {

				Long idIssue = featuresIssue.getId();

				boolean condition = false;

				condition = idIssue.equals(issueId);

				while (condition) {
					return ResponseEntity.ok().body(featuresIssue);
				}

			}
		}

		return ResponseEntity.notFound().build();

	}

	@SuppressWarnings("finally")
	@PostMapping("/issue/{backlogId}/{featuresId}")
	public ResponseEntity<Issue[]> createIssue(@Validated @RequestBody Issue issue, @PathVariable Long featuresId,
			@PathVariable Long backlogId) throws ResourceNotFoundException {

		BackLog backLog = sprintsAppRepository.findById(backlogId)
				.orElseThrow(() -> new ResourceNotFoundException("El BackLog not found for this id :: " + backlogId));

		issue.setId(sequenceGeneratorService.generateSequence(Issue.SEQUENCE_NAME));
		issue.setCreatedAt(new Date());

		Features[] backlogFeatures = backLog.getFeatures();
		for (int i = 0; i < backlogFeatures.length; i++) {
			Long id = backlogFeatures[i].getId();
			if (id == featuresId) {
				Issue[] issuesAll = backlogFeatures[i].getIssues();
				Issue[] issuesAdd = new Issue[issuesAll.length + 1];
				for (int j = 0; j < issuesAll.length; j++) {
					issuesAdd[j] = issuesAll[j];
				}
				issuesAdd[issuesAll.length] = issue;
				backlogFeatures[i].setIssues(issuesAdd);
				backLog.setFeatures(backlogFeatures);
				sprintsAppRepository.save(backLog);
				try {
					storeNotification(0, null, null, issue);
				} finally {
					// TODO: handle finally clause
					return ResponseEntity.ok().body(issuesAdd);
				}
			}
		}
		return ResponseEntity.notFound().build();

	}

	@SuppressWarnings("finally")
	@PutMapping("/issue/{issueId}")
	public ResponseEntity<Issue[]> updateIssue(@PathVariable Long issueId, @Validated @RequestBody Issue issueDetails)
			throws ResourceNotFoundException {

		List<BackLog> backLog = sprintsAppRepository.findAll();

		for (int k = 0; k < backLog.size(); k++) {

			BackLog backlogs = backLog.get(k);
			Features[] features = backlogs.getFeatures();

			for (Features backlogFeature : features) {

				Issue[] issueFind = backlogFeature.getIssues();

				boolean condition = false;

				for (int i = 0; i < issueFind.length; i++) {

					Long idIssue = issueFind[i].getId();
					condition = idIssue.equals(issueId);

					if (condition) {

//						Issue[] issue = backlogFeature.getIssues();

//						for (int j = 0; j < issueFind.length; j++) {

						issueFind[i].setTitle(issueDetails.getTitle());
						issueFind[i].setDescription(issueDetails.getDescription());
						issueFind[i].setEmail(issueDetails.getEmail());
						issueFind[i].setProject(issueDetails.getProject());
						issueFind[i].setCreatedAt(new Date());
						issueFind[i].setEstado(issueDetails.getEstado());
						
						backlogFeature.setIssues(issueFind);

						sprintsAppRepository.save(backlogs);

						try {
							if (issueDetails.getEstado() == 3) {
								storeNotification(2, backLogNULL, featuresNULL, issueDetails);
							} else if (true) {
								storeNotification(1, backLogNULL, featuresNULL, issueDetails);
							}
						} finally {
							// TODO: handle finally clause
							return ResponseEntity.ok().body(issueFind);
						}

//							issue[j].setTitle(issueDetails.getTitle());
//							issue[j].setDescription(issueDetails.getDescription());
//							issue[j].setEmail(issueDetails.getEmail());
//							issue[j].setProject(issueDetails.getProject());
//							issue[j].setCreatedAt(new Date());
//							issue[j].setEstado(issueDetails.getEstado());
//						}
					}
				}
			}
		}
		return null;
	}
}