package vuesample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import vuesample.service.ImportTestDataService;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	ImportTestDataService importTestDataService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		try {
			importTestDataService.importTestData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
