package com.foundation.validador;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class ValidadorBasicBuilder extends AbstractValidadorBuilder {

	public static ValidadorBasicBuilder newInstance() {
		return new ValidadorBasicBuilder();
	}
	
}
