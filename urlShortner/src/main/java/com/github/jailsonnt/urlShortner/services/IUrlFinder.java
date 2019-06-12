package com.github.jailsonnt.urlShortner.services;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;

public interface IUrlFinder {
	public UrlsLink findCreatedUrl(String shortenedIdentifier) throws UrlExpiredException, UrlNotFoundException;
	public UrlsLink findOriginalUrl(String originalUrl) throws UrlNotFoundException;

}
