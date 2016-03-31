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
package org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs1_8;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.activelist.Allergy;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestTestConstants1_8;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.AllergyResource1_8;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;

public class AllergyController1_8Test extends MainResourceControllerTest {
	
	private static final String ACTIVE_LIST_INITIAL_XML = "customActiveListTest.xml";
	
	@Before
	public void init() throws Exception {
		executeDataSet(ACTIVE_LIST_INITIAL_XML);
	}
	
	/**
	 * @see AllergyController#getAllergy(String,WebRequest)
	 * @verifies get a default representation of an allergy
	 */
	@Test
	public void getAllergy_shouldGetADefaultRepresentationOfAnAllergy() throws Exception {
		SimpleObject result = deserialize(handle(newGetRequest(getURI() + "/" + RestTestConstants1_8.ALLERGY_UUID)));
		Assert.assertNotNull(result);
		Util.log("Allergy fetched (default)", result);
		Assert.assertEquals(RestTestConstants1_8.ALLERGY_UUID, PropertyUtils.getProperty(result, "uuid"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "links"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "person"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "allergen"));
		Assert.assertNull(PropertyUtils.getProperty(result, "auditInfo"));
	}
	
	/**
	 * @see AllergyController#getAllergy(String,WebRequest)
	 * @verifies get a full representation of an allergy
	 */
	@Test
	public void getAllergy_shouldGetAFullRepresentationOfAnAllergy() throws Exception {
		MockHttpServletRequest req = newGetRequest(getURI() + "/" + RestTestConstants1_8.ALLERGY_UUID, new Parameter(
		        RestConstants.REQUEST_PROPERTY_FOR_REPRESENTATION, RestConstants.REPRESENTATION_FULL));
		SimpleObject result = deserialize(handle(req));
		Assert.assertNotNull(result);
		Util.log("Allergy fetched (default)", result);
		Assert.assertEquals(RestTestConstants1_8.ALLERGY_UUID, PropertyUtils.getProperty(result, "uuid"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "links"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "person"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "allergen"));
		Assert.assertNotNull(PropertyUtils.getProperty(result, "auditInfo"));
	}
	
	/**
	 * @see AllergyController#voidAllergy(String,String,WebRequest,HttpServletResponse)
	 * @verifies void an allergy
	 */
	@Test
	public void voidAllergy_shouldVoidAnAllergy() throws Exception {
		Allergy allergy = Context.getPatientService().getAllergy(1);
		Assert.assertFalse(allergy.isVoided());
		
		handle(newDeleteRequest(getURI() + "/" + RestTestConstants1_8.ALLERGY_UUID, new Parameter("reason", "unit test")));
		allergy = Context.getPatientService().getAllergy(1);
		Assert.assertTrue(allergy.isVoided());
		Assert.assertEquals("unit test", allergy.getVoidReason());
	}
	
	/**
	 * @see AllergyResource1_8#getAllergyByPatient(String,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 * @throws Exception
	 */
	@Test
	public void searchByPatient_shouldGetAllergyForAPatient() throws Exception {
		MockHttpServletRequest req = newGetRequest(getURI(),
		    new Parameter("patient", "da7f524f-27ce-4bb2-86d6-6d1d05312bd5"));
		SimpleObject result = deserialize(handle(req));
		List<Object> results = Util.getResultsList(result);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(RestTestConstants1_8.ALLERGY_UUID, PropertyUtils.getProperty(results.get(0), "uuid"));
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest#getURI()
	 */
	@Override
	public String getURI() {
		return "allergy";
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest#getUuid()
	 */
	@Override
	public String getUuid() {
		return RestTestConstants1_8.ALLERGY_UUID;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest#getAllCount()
	 */
	@Override
	public long getAllCount() {
		return 0;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest#shouldGetAll()
	 */
	@Override
	@Test(expected = ResourceDoesNotSupportOperationException.class)
	public void shouldGetAll() throws Exception {
		super.shouldGetAll();
	}
}
