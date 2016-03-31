/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.data.patient.evaluator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.CalculationRegistration;
import org.openmrs.calculation.ClasspathCalculationProvider;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.reporting.common.TestUtil;
import org.openmrs.module.reporting.data.patient.EvaluatedPatientData;
import org.openmrs.module.reporting.data.patient.definition.PatientCalculationDataDefinition;
import org.openmrs.module.reporting.data.patient.service.PatientDataService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.annotation.ExpectedException;

/**
 * Test class for {@link PatientCalculationDataEvaluator}
 */
public class PatientCalculationDataEvaluatorTest extends BaseModuleContextSensitiveTest {

	protected static final String XML_DATASET_PATH = "org/openmrs/module/reporting/include/";

	protected static final String XML_REPORT_TEST_DATASET = "ReportTestDataset";

	/**
	 * Run this before each unit test in this class. The "@Before" method in {@link org.openmrs.test.BaseContextSensitiveTest}
	 * is run right before this method.
	 *
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		executeDataSet(XML_DATASET_PATH + new TestUtil().getTestDatasetFilename(XML_REPORT_TEST_DATASET));
	}

	/**
	 * @verifies return data generated by the calculation referenced by the definition
	 * @see PatientCalculationDataEvaluator#evaluate(org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition,
	 *      org.openmrs.module.reporting.evaluation.EvaluationContext)
	 */
	@Test
	public void evaluate_shouldReturnDataGeneratedByTheCalculationReferencedByTheDefinition() throws Exception {
		// set up the context
		EvaluationContext context = new EvaluationContext();
		context.setBaseCohort(new Cohort("7,-1"));

		// create a registration to put on the definition
		CalculationRegistration registration = new CalculationRegistration();
		registration.setToken("Test");
		registration.setCalculationName(TestPatientCalculation.class.getCanonicalName());
		registration.setProviderClassName(ClasspathCalculationProvider.class.getCanonicalName());

		// create a definition
		PatientCalculationDataDefinition d = new PatientCalculationDataDefinition("Example", registration);

		// evaluate it
		EvaluatedPatientData pd = Context.getService(PatientDataService.class).evaluate(d, context);

		// check a valid entry
		CalculationResult result = (CalculationResult) pd.getData().get(7);
		Assert.assertEquals("5946f880-b197-400b-9caa-a3c661d23041", result.getValue());

		// check an invalid entry
		result = (CalculationResult) pd.getData().get(-1);
		Assert.assertEquals(null, result.getValue());
	}

	/**
	 * @verifies throw an error if no CalculationRegistration exists on the definition
	 * @see PatientCalculationDataEvaluator#evaluate(org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition,
	 *      org.openmrs.module.reporting.evaluation.EvaluationContext)
	 */
	@Test
	@ExpectedException(value = EvaluationException.class)
	public void evaluate_shouldThrowAnErrorIfNoCalculationRegistrationExistsOnTheDefinition() throws Exception {
		EvaluationContext context = new EvaluationContext();
		context.setBaseCohort(new Cohort("7"));
		PatientCalculationDataDefinition d = new PatientCalculationDataDefinition("Example");
		Context.getService(PatientDataService.class).evaluate(d, context);
	}
}
