package com.github.jailsonnt.urlShortner.services;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.repository.UrlsLinkRepository;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;

@Service
public class UrlShortenerService implements IUrlShortnerService, IUrlFinder {
	private Logger logger = LogManager.getLogger(getClass());
	private UrlsLinkRepository repositorio;
	
	@Autowired
	public UrlShortenerService(UrlsLinkRepository repositorio) {
		this.repositorio = repositorio;
	}
	@Override
	public UrlsLink findUrl(String url) throws UrlExpiredException, UrlNotFoundException {
		long id = Long.parseLong("10", 16);
		logger.info("novo hexa gerado 10 de " + id);
		//certo, funciona
		return null;
	}

	@Override
	public UrlsLink shortenUrl(String url, String urlBase) throws Exception {
		//validar urlExistente ddentro da data de expiração ... nesse caso retorna a url já existente
		UrlsLink newUrl = new UrlsLink(url);
		newUrl = repositorio.save(newUrl);
		String hexadecimalId = Long.toHexString(newUrl.getId());
		logger.info("novo hexa gerado "+hexadecimalId+ " de " + newUrl.getId());
		newUrl.setCreatedURL(urlBase+"/short"+hexadecimalId);
		newUrl.setExpirationDate(new Date());
		newUrl = repositorio.save(newUrl);
		return newUrl;
	}

}
