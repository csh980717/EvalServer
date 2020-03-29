package live.bokurano.evalserver.controller;

import live.bokurano.evalserver.common.JsonResult;
import live.bokurano.evalserver.common.ServerResult;
import live.bokurano.evalserver.service.EvaluationService;
import live.bokurano.evalserver.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class GeneralController {
	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@GetMapping("/api/getCourseEvaluation")
	public JsonResult getCourseEvaluation(@RequestParam("courseId") String courseId) {
		JsonResult result = new JsonResult();
		ServerResult serverResult = evaluationService.getCourseEvaluations(courseId);
		if (serverResult.isSuccess()) {
			result.setStatus(HttpStatus.OK.value());
		} else {
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(serverResult.getMessage());
		}
		return result;
	}

	@GetMapping("/api/getCourseInfo")
	public JsonResult getCourseInfo(@RequestParam("courseId") String courseId) {
		JsonResult result = new JsonResult();
		ServerResult serverResult = evaluationService.getCourseInfo(courseId);
		if (serverResult.isSuccess()) {
			result.setStatus(HttpStatus.OK.value());
		} else {
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(serverResult.getMessage());
		}
		return result;
	}

	@GetMapping("/api/getCurrentRole")
	public String getCurrentRole(@RequestParam("username") String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
	}
}
