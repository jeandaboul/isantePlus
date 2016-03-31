/*
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

package org.openmrs.module.reporting.data.encounter.definition;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

import java.util.ArrayList;
import java.util.List;

/**
 * Fetches other encounters with the same patient and encounterDatetime as the one in question
 * E.g. you could find any encounters of type "Admission" simultaneous to visit note encounters
 */
@Caching(strategy=ConfigurationPropertyCachingStrategy.class)
public class SimultaneousEncountersDataDefinition extends BaseDataDefinition implements EncounterDataDefinition {

    public static final long serialVersionUID = 1L;

    @ConfigurationProperty
    private List<EncounterType> encounterTypeList;

    public void addEncounterType(EncounterType encounterType) {
        if (encounterTypeList == null) {
            encounterTypeList = new ArrayList<EncounterType>();
        }
        encounterTypeList.add(encounterType);
    }

    public List<EncounterType> getEncounterTypeList() {
        return encounterTypeList;
    }

    public void setEncounterTypeList(List<EncounterType> encounterTypeList) {
        this.encounterTypeList = encounterTypeList;
    }

    @Override
    public Class<?> getDataType() {
        return Encounter.class;
    }
}
