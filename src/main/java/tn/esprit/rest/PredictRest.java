package tn.esprit.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.machinelearning.PredictComeBack;

@RestController
@RequestMapping("/prediction")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PredictRest {
	
	private PredictComeBack predictionComeBack = new PredictComeBack();
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String comeback(@PathVariable("id") Long id) throws Exception{
		Double pred = predictionComeBack.Test(id);
		String predString =Double.toString(pred);
		return predString;
		
	}

}
