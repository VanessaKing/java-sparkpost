package com.sparkpost.samples;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.Response;
import com.sparkpost.model.TemplateItem;
import com.sparkpost.model.TemplateListResponse;
import com.sparkpost.resources.ResourceTemplates;
import com.sparkpost.sdk.samples.helpers.SparkPostBaseApp;
import com.sparkpost.transport.RestConnection;

/**
 * Delete all test templates created by the sample code
 */
public class DeleteSampleTemplates extends SparkPostBaseApp {
	static final Logger logger = Logger.getLogger(DeleteSampleTemplates.class);

	private Client client;
	
	public static void main(String[] args) throws SparkPostException, IOException {
		Logger.getRootLogger().setLevel(Level.DEBUG);

		DeleteSampleTemplates app = new DeleteSampleTemplates();
		app.runApp();
	}
	
	private void runApp() throws SparkPostException, IOException {
		client = this.newConfiguredClient();
		RestConnection connection = new RestConnection(client, getEndPoint());
		
		// Get All Templates
		TemplateListResponse listResponse = ResourceTemplates.listAll(connection);
		List<TemplateItem> results = listResponse.getResults();
		
		for (TemplateItem item : results) {
			
			// Delete any template with the name  "_TMP_TEMPLATE_TEST"
			if (item.getName().equals(SAMPLE_TEMPLATE_NAME)) {
				deleteTemplate(connection, item.getId());
			}
		}
	}

	private void deleteTemplate(RestConnection connection, String templateId) throws SparkPostException {
		Response deleteResponse = ResourceTemplates.delete(connection, templateId);
		if (deleteResponse.getResponseCode() == 200) {
			System.out.println("\tdeleted: " + templateId);
		} else {
			System.out.println("\tError: Failed to delete: " +templateId + ") because " + deleteResponse.getResponseMessage());
		}
	}
}