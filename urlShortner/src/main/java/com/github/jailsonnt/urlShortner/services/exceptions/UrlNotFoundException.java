package com.github.jailsonnt.urlShortner.services.exceptions;

public class UrlNotFoundException extends Exception{
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof UrlNotFoundException) {
			return true;
		}
		return false;
	}
}
