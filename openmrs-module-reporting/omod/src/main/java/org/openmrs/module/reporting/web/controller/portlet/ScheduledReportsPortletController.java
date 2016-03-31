package org.openmrs.module.reporting.web.controller.portlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.service.ReportService;


public class ScheduledReportsPortletController extends ReportingPortletController {
	
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		super.populateModel(request, model);
		ReportService service = Context.getService(ReportService.class);
		List<ReportRequest> rpts = service.getReportRequests(null, null, null, Status.SCHEDULED);
		model.put("scheduled", rpts);
	}

}
