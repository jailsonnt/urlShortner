package com.github.jailsonnt.urlShortner.services;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public interface IUrlShortnerService {
	public UrlsLink shortenUrl(String url, String urlBase) throws Exception;
}
