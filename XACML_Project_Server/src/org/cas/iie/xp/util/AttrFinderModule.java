package org.cas.iie.xp.util;

import com.sun.xacml.EvaluationCtx;

import com.sun.xacml.attr.AttributeDesignator;
import com.sun.xacml.attr.BagAttribute;
import com.sun.xacml.attr.RFC822NameAttribute;
import com.sun.xacml.attr.StringAttribute;

import com.sun.xacml.cond.EvaluationResult;

import com.sun.xacml.ctx.Status;

import com.sun.xacml.finder.AttributeFinderModule;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AttrFinderModule extends AttributeFinderModule {

	private static final String SUPPORTED_ATTRIBUTE_ID = "subject-groups";

	private static final String USER_ID = "urn:oasis:names:tc:xacml:1.0:subject:subject-id";

	private static final String USER_ID_TYPE = RFC822NameAttribute.identifier;

	private URI userId;

	private URI userIdType;

	public AttrFinderModule() throws URISyntaxException {

		userId = new URI(USER_ID);

		userIdType = new URI(USER_ID_TYPE);

	}

	public boolean isDesignatorSupported() {

		return true;
	}

	public Set getSupportedDesignatorTypes() {

		Set types = new HashSet();

		types.add(new Integer(AttributeDesignator.SUBJECT_TARGET));

		return types;
	}

	public Set getSupportedIds() {

		Set ids = new HashSet();

		try {
			ids.add(new URI(SUPPORTED_ATTRIBUTE_ID));

		} catch (URISyntaxException se) {

			return null;
		}

		return ids;
	}

	public EvaluationResult findAttribute(URI attributeType, URI attributeId,
			URI issuer, URI subjectCategory, EvaluationCtx context,
			int designatorType) {

		if (designatorType != AttributeDesignator.SUBJECT_TARGET)

			return new EvaluationResult(
					BagAttribute.createEmptyBag(attributeType));

		if ((!attributeType.toString().equals(StringAttribute.identifier))
				|| (!attributeId.toString().equals(SUPPORTED_ATTRIBUTE_ID)))

			return new EvaluationResult(
					BagAttribute.createEmptyBag(attributeType));

		EvaluationResult result = context.getSubjectAttribute(userIdType,
				userId, subjectCategory);

		if (result.indeterminate())
			return result;

		BagAttribute bag = (BagAttribute) (result.getAttributeValue());

		if (bag.size() != 1) {

			ArrayList code = new ArrayList();

			code.add(Status.STATUS_PROCESSING_ERROR);

			Status status = new Status(code, "couldn't find user's identifier");

			return new EvaluationResult(status);
		}

		RFC822NameAttribute user = (RFC822NameAttribute) (bag.iterator().next());

		return getGroups(user);
	}

	private EvaluationResult getGroups(RFC822NameAttribute user) {

		BagAttribute groups = null;

		return new EvaluationResult(groups);
	}

}
