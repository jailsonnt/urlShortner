package com.github.jailsonnt.urlShortner.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.repository.UrlsLinkRepository;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;

@Service
public class UrlShortenerService implements IUrlShortnerService, IUrlFinder {
	private Logger logger = LogManager.getLogger(getClass());
	private UrlsLinkRepository repositorio;
	@Value("${urlShortener.days-of-validity-of-Url:5}")
	private int defaultValidity;
	@Value("${urlShortener.baseurl:http://localhost:8443}")
	private String urlBase;
	
	@Autowired
	public UrlShortenerService(UrlsLinkRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@Override
	public UrlsLink findCreatedUrl(String shortenedIdentifier) throws UrlExpiredException, UrlNotFoundException {
		long id = Long.parseLong(getIdFromIdentifier(shortenedIdentifier), 16);
		Optional<UrlsLink> urlsLink = repositorio.findById(id);
		if (!urlsLink.isPresent()) {
			throw new UrlNotFoundException();
		} else {
			if (checkDateIsInFuture(urlsLink.get().getExpirationDate())) {
				return urlsLink.get();
			}
			throw new UrlExpiredException();
		}
	}
	
	private String getIdFromIdentifier(String shortenedIdentifier) {
		return shortenedIdentifier.replace("short", "");
	}
	
	private boolean checkDateIsInFuture(Date dataHora) {
		Instant dataHoraInstant = Instant.ofEpochMilli(dataHora.getTime());
		LocalDateTime dataHoraLocalTime = LocalDateTime.ofInstant(dataHoraInstant, ZoneId.systemDefault());
		if (dataHoraLocalTime.isAfter(LocalDateTime.now())) {
			return true;
		}
		return false;
	}
	
	@Override
	public UrlsLink findOriginalUrl(String originalUrl) throws UrlNotFoundException {
		Optional<List<UrlsLink>> urlList = repositorio.findByOriginalURLAndExpirationDate(originalUrl, new Date());
		if (urlList.isPresent() && !urlList.get().isEmpty()) {
			return urlList.get().get(0);
		}
		throw new UrlNotFoundException();
	}

	@Override
	public UrlsLink shortenUrl(String url,  Optional<Integer> daysOfValidity) throws Exception {
		UrlsLink newUrl = new UrlsLink(url);
		newUrl = repositorio.save(newUrl);
		String hexadecimalId = Long.toHexString(newUrl.getId());
		newUrl.setCreatedURL(urlBase+"/short"+hexadecimalId);
		newUrl.setExpirationDate(getValidity(daysOfValidity));
		newUrl = repositorio.save(newUrl);
		return newUrl;
	}
	
	private Date getValidity(Optional<Integer> daysOfValidity) {
		int choosenValidity = chooseValidity(daysOfValidity);
		return addDaysToDate(choosenValidity);
	}
	
	private int chooseValidity(Optional<Integer> daysOfValidity) {
		if (daysOfValidity.isPresent()) {
			return daysOfValidity.get();
		} else {
			return defaultValidity;
		}
	}
	
	private Date addDaysToDate(int days) {
		LocalDateTime newDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		Instant newDateInstant = newDate.plusDays(days).atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(newDateInstant);
	}

}
