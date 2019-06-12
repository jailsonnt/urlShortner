package com.github.jailsonnt.urlShortner.services;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;

public interface IUrlFinder {
	public UrlsLink findUrl(String url) throws UrlExpiredException, UrlNotFoundException;

}
