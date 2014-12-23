package org.cas.iie.xp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.cas.iie.xp.model.UserInfo;

import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.Indenter;
import com.sun.xacml.attr.AnyURIAttribute;
import com.sun.xacml.attr.RFC822NameAttribute;
import com.sun.xacml.attr.StringAttribute;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.Subject;

public class MyRequestBuilder {

	public static Set setupSubjects(String sub)
			throws URISyntaxException {

		HashSet attributes = new HashSet();

		URI subjectId = new URI(
				"urn:oasis:names:tc:xacml:1.0:subject:subject-id");

		//RFC822NameAttribute value = new RFC822NameAttribute(sub);

		attributes.add(new Attribute(subjectId, null, null, new StringAttribute(sub)));

		HashSet subjects = new HashSet();

		subjects.add(new Subject(attributes));

		return subjects;
	}

	public static Set setupResource(String identifier)
			throws URISyntaxException {

		HashSet resource = new HashSet();

		AnyURIAttribute value = new AnyURIAttribute(new URI(identifier));

		resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID), null,
				null, value));

		return resource;
	}

	public static Set setupAction(String userAction) throws URISyntaxException {

		HashSet action = new HashSet();

		URI actionId = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

		action.add(new Attribute(actionId, null, null, new StringAttribute(
				userAction)));

		return action;
	}

	public String doBuild(UserInfo ui) throws Exception {

		RequestCtx request = new RequestCtx(setupSubjects(
				ui.getGroup()), setupResource(ui.getResGroup()),
				setupAction(ui.getResAction()), new HashSet());

		request.encode(System.out, new Indenter());

		DocumentPath dp = new DocumentPath();

		String temp = dp.getPath().get("request");

		DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		String requestName = format.format(new Date());

		String filePath = temp + requestName + ".xml";

		File location = new File(filePath);

		FileOutputStream os = new FileOutputStream(location);
		
		request.encode(os);
		
		os.flush();
		
		os.close();
		
		return requestName + ".xml";

	}

}
