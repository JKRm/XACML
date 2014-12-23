package org.cas.iie.xp.util;

import com.sun.xacml.Indenter;
import com.sun.xacml.Policy;
import com.sun.xacml.Rule;
import com.sun.xacml.Target;
import com.sun.xacml.TargetMatch;
import com.sun.xacml.UnknownIdentifierException;

import com.sun.xacml.attr.AnyURIAttribute;
import com.sun.xacml.attr.AttributeDesignator;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.attr.StringAttribute;

import com.sun.xacml.combine.CombiningAlgFactory;
import com.sun.xacml.combine.OrderedPermitOverridesRuleAlg;
import com.sun.xacml.combine.RuleCombiningAlgorithm;

import com.sun.xacml.cond.Apply;
import com.sun.xacml.cond.Function;
import com.sun.xacml.cond.FunctionFactory;

import com.sun.xacml.ctx.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cas.iie.xp.model.UserInfo;

public class MyPolicyBuilder {

	public static TargetMatch createTargetMatch(int type, String functionId,
			AttributeDesignator designator, AttributeValue value) {

		try {
			FunctionFactory factory = FunctionFactory.getTargetInstance();

			Function function = factory.createFunction(functionId);

			return new TargetMatch(type, function, designator, value);

		} catch (Exception e) {

			return null;
		}
	}

	public static Target createPolicyTarget(String userGroup, String resourceId)
			throws URISyntaxException {

		List<List<TargetMatch>> subjects = new ArrayList<List<TargetMatch>>();

		List<List<TargetMatch>> resources = new ArrayList<List<TargetMatch>>();

		List<TargetMatch> subject = new ArrayList<TargetMatch>();
		

		String subjectMatchId = "urn:oasis:names:tc:xacml:1.0:function:string-equal";

		URI subjectDesignatorType = new URI(
				"http://www.w3.org/2001/XMLSchema#string");

		URI subjectDesignatorId = new URI(
				"urn:oasis:names:tc:xacml:1.0:subject:subject-id");

		AttributeDesignator subjectDesignator = new AttributeDesignator(
				AttributeDesignator.SUBJECT_TARGET, subjectDesignatorType,
				subjectDesignatorId, false);

		StringAttribute subjectValue = new StringAttribute(userGroup);

		subject.add(createTargetMatch(TargetMatch.SUBJECT, subjectMatchId,
				subjectDesignator, subjectValue));

		List<TargetMatch> resource = new ArrayList<TargetMatch>();

		String resourceMatchId = "urn:oasis:names:tc:xacml:1.0:function:anyURI-equal";

		URI resourceDesignatorType = new URI(
				"http://www.w3.org/2001/XMLSchema#anyURI");

		URI resourceDesignatorId = new URI(
				"urn:oasis:names:tc:xacml:1.0:resource:resource-id");

		AttributeDesignator resourceDesignator = new AttributeDesignator(
				AttributeDesignator.RESOURCE_TARGET, resourceDesignatorType,
				resourceDesignatorId, false);

		AnyURIAttribute resourceValue = new AnyURIAttribute(new URI(resourceId));

		resource.add(createTargetMatch(TargetMatch.RESOURCE, resourceMatchId,
				resourceDesignator, resourceValue));

		subjects.add(subject);

		resources.add(resource);
		

		return new Target(subjects, resources, null);
	}

	
	 public static Target createRuleTarget(String useraction) throws URISyntaxException {
		 
	        List<List<TargetMatch>> actions = new ArrayList<List<TargetMatch>>();
	        
	        List<TargetMatch> action = new ArrayList<TargetMatch>();

	        String actionMatchId =
	            "urn:oasis:names:tc:xacml:1.0:function:string-equal";

	        URI actionDesignatorType =
	            new URI("http://www.w3.org/2001/XMLSchema#string");
	        
	        URI actionDesignatorId =
	            new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");
	        
	        
	        AttributeDesignator actionDesignator =
	            new AttributeDesignator(AttributeDesignator.ACTION_TARGET,
	                                    actionDesignatorType,
	                                    actionDesignatorId, false);
	        

	        StringAttribute actionValue = new StringAttribute(useraction);
	        
	        action.add(createTargetMatch(TargetMatch.ACTION, actionMatchId,
	                                     actionDesignator, actionValue));

	        actions.add(action);

	        return new Target(null, null, actions);
	    }
	

	public static Rule createRule(String userAction) throws URISyntaxException {

		URI ruleId = new URI("readRule");

		int effect = Result.DECISION_PERMIT;

		Target target = createRuleTarget(userAction);

		return new Rule(ruleId, effect, null, target, null);
	}
	
	
	public static Rule createFinalRule() throws URISyntaxException {

		URI ruleId = new URI("final");

		int effect = Result.DECISION_DENY;

		return new Rule(ruleId, effect, null, null, null);
	}
	

	public String policyBuild(UserInfo ui, String[] actions) throws Exception {

		URI policyId = new URI("GeneratedPolicy");

		URI combiningAlgId = new URI(OrderedPermitOverridesRuleAlg.algId);

		CombiningAlgFactory factory = CombiningAlgFactory.getInstance();

		RuleCombiningAlgorithm combiningAlg = (RuleCombiningAlgorithm) (factory
				.createAlgorithm(combiningAlgId));

		String description = "XACML_project policy";

		Target policyTarget = createPolicyTarget(ui.getGroup(),ui.getResGroup());
		
		List<Rule> ruleList = new ArrayList<Rule>();

		for(int i=0; i<actions.length; i++){
			
			Rule rule = createRule(actions[i]);
			
			ruleList.add(rule);
		}
		
		Rule finalRule = createFinalRule();
		
		ruleList.add(finalRule);

		Policy policy = new Policy(policyId, combiningAlg, description,
				policyTarget, ruleList);

		DocumentPath dp = new DocumentPath();

		String temp = dp.getPath().get("policy");

		DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		String policyName = format.format(new Date());

		String filePath = temp + policyName + ".xml";

		System.out.println(filePath);

		File location = new File(filePath);

		FileOutputStream os = new FileOutputStream(location);

		policy.encode(os);
		
		os.flush();
		
		os.close();

		return policyName + ".xml";
	}

}
