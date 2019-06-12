package com.github.jailsonnt.urlShortner.services.exceptions;

public class UrlExpiredException extends Exception {
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof UrlExpiredException) {
			return true;
		}
		return false;
	}
	
}
