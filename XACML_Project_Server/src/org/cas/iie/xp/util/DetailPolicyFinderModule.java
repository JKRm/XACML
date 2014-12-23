package org.cas.iie.xp.util;

import com.sun.xacml.AbstractPolicy;
import com.sun.xacml.ParsingException;
import com.sun.xacml.Policy;
import com.sun.xacml.PolicyReference;
import com.sun.xacml.PolicySet;

import com.sun.xacml.ctx.Status;

import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.PolicyFinderModule;
import com.sun.xacml.finder.PolicyFinderResult;

import java.net.URI;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class DetailPolicyFinderModule extends PolicyFinderModule {

	public static final String REFERENCE_NAMESPACE = "urn:my:custom:space";

	private PolicyFinder finder;

	public DetailPolicyFinderModule() {

	}

	public boolean isIdReferenceSupported() {
		return true;
	}

	public void init(PolicyFinder finder) {

		this.finder = finder;
	}

	public PolicyFinderResult findPolicy(URI idReference, int type) {

		if (!idReference.toString().startsWith(REFERENCE_NAMESPACE))

			return new PolicyFinderResult();

		Node root = null;

		AbstractPolicy policy;

		try {
			if (type == PolicyReference.POLICY_REFERENCE) {

				policy = Policy.getInstance(root);

			} else {

				policy = PolicySet.getInstance(root, finder);
			}
		} catch (ParsingException pe) {

			ArrayList code = new ArrayList();

			String message = "error parsing referenced policy \"" +

			idReference.toString() + "\": " + pe.getMessage();

			code.add(Status.STATUS_SYNTAX_ERROR);

			return new PolicyFinderResult(new Status(code, message));
		}

		return new PolicyFinderResult(policy);
	}

}
