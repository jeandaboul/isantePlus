/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.ConceptClass;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates attributes on the {@link ConceptClass} object.
 * 
 * @since 1.5
 */
@Handler(supports = { ConceptClass.class }, order = 50)
public class ConceptClassValidator implements Validator {
	
	/** Log for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Determines if the command object being submitted is a valid type
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class c) {
		return c.equals(ConceptClass.class);
	}
	
	/**
	 * Checks the form object for any inconsistencies/errors
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 * @should fail validation if user is null or empty or whitespace
	 * @should fail validation if name is already exist in non retired concept class
	 * @should pass validation if description is null or empty or whitespace
	 * @should pass validation if all required fields have proper values
	 */
	
	public void validate(Object obj, Errors errors) {
		ConceptClass cc = (ConceptClass) obj;
		if (cc == null) {
			errors.rejectValue("conceptClass", "error.general");
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name");
			if (!errors.hasErrors()) {
				ConceptClass exist = Context.getConceptService().getConceptClassByName(cc.getName());
				if (exist != null && !exist.isRetired() && !OpenmrsUtil.nullSafeEquals(cc.getUuid(), exist.getUuid())) {
					errors.rejectValue("name", "conceptclass.duplicate.name");
				}
			}
		}
	}
	
}
