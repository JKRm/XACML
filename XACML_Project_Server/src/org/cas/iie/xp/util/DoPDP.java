package org.cas.iie.xp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.xacml.ConfigurationStore;
import com.sun.xacml.Indenter;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ParsingException;
import com.sun.xacml.cond.FunctionFactory;
import com.sun.xacml.cond.FunctionFactoryProxy;
import com.sun.xacml.cond.StandardFunctionFactory;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import com.sun.xacml.finder.impl.SelectorModule;

public class DoPDP {

	private PDP pdp = null;

	public DoPDP() throws Exception {

		ConfigurationStore store = new ConfigurationStore();

		store.useDefaultFactories();

		pdp = new PDP(store.getDefaultPDPConfig());
	}

	public DoPDP(String[] policyFiles) throws Exception {

		FilePolicyModule filePolicyModule = new FilePolicyModule();

		for (int i = 0; i < policyFiles.length; i++)

			filePolicyModule.addPolicy(policyFiles[i]);

		PolicyFinder policyFinder = new PolicyFinder();

		Set policyModules = new HashSet();

		policyModules.add(filePolicyModule);
		
		policyFinder.setModules(policyModules);

		CurrentEnvModule envAttributeModule = new CurrentEnvModule();

		SelectorModule selectorAttributeModule = new SelectorModule();

		AttributeFinder attributeFinder = new AttributeFinder();

		List attributeModules = new ArrayList();

		attributeModules.add(envAttributeModule);

		attributeModules.add(selectorAttributeModule);

		attributeFinder.setModules(attributeModules);

		FunctionFactoryProxy proxy = StandardFunctionFactory
				.getNewFactoryProxy();

		FunctionFactory factory = proxy.getConditionFactory();

		factory.addFunction(new TimeInRangeFunction());

		FunctionFactory.setDefaultFactory(proxy);

		pdp = new PDP(new PDPConfig(attributeFinder, policyFinder, null));
	}

	public ResponseCtx evaluate(String requestFile) throws IOException,
			ParsingException {

		RequestCtx request = RequestCtx.getInstance(new FileInputStream(
				requestFile));

		return pdp.evaluate(request);
	}

	public String doPDP(String requestFile, String[] policyFiles)
			throws Exception {

		DoPDP simplePDP = null;

		simplePDP = new DoPDP(policyFiles);

		ResponseCtx response = simplePDP.evaluate(requestFile);

		response.encode(System.out, new Indenter());

		Set<Result> results = new HashSet<Result>();

		results = response.getResults();

		Iterator i = results.iterator();

		String output = null;

		while (i.hasNext()) {

			Result r = (Result) i.next();

			output = r.DECISIONS[r.getDecision()];
		}

		return output;
	}

}
