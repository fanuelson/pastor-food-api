package com.foundation.validador;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class ValidadorClienteBuilder extends AbstractValidadorBuilder {

	public static ValidadorClienteBuilder newInstance() {
		return new ValidadorClienteBuilder();
	}
	
}
