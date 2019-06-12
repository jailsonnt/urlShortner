package com.github.jailsonnt.urlShortner.services;

import java.util.Optional;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public interface IUrlShortnerService {
	public UrlsLink shortenUrl(String url, Optional<Integer> daysOfValidity) throws Exception;
}
