package com.creativehub.backend.util;

import com.creativehub.backend.services.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventsListener {
	private final UserManager userManager;

	@EventListener
	public void appReady(ApplicationReadyEvent ignored) {
		userManager.setupRootUser();
	}
}
