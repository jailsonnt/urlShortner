package com.github.jailsonnt.urlShortner.services.mock;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.repository.UrlsLinkRepository;

public class UrlLinkRepositoryMock implements UrlsLinkRepository{
	private Map<Long, UrlsLink> idMap;
	private Map<String,List<UrlsLink>> originalUrlMap;
	private long lastId = 1;
	
	public UrlLinkRepositoryMock() {
		idMap = new HashMap<Long, UrlsLink>();
		originalUrlMap = new HashMap<String,List<UrlsLink>>();
	}
	@Override
	public Optional<UrlsLink> findById(Long id) {
		UrlsLink url = idMap.get(id);
		if (url == null) {
			return Optional.empty();
		}
		return Optional.of(url);
	}
	
	@Override
	public Optional<List<UrlsLink>> findByOriginalURLAndExpirationDate(String originalURL, Date now) {
		if (originalUrlMap.containsKey(originalURL)) {
			List<UrlsLink> urlFullList = originalUrlMap.get(originalURL);
			List<UrlsLink> urlFilteredList = new ArrayList<UrlsLink>();
			for (UrlsLink link: urlFullList) {
				if (checkDateIsInFuture(link.getExpirationDate())) {
					urlFilteredList.add(link);
				}
			}
			return Optional.of(urlFilteredList);
		}
		return Optional.empty();
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
	public synchronized <S extends UrlsLink> S save(S entity) {
		if (entity.getId() == null) {
			return create(entity);
		} else {
			return update(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <S extends UrlsLink> S create(S entity) {
		UrlsLinkMock mockEntity = new UrlsLinkMock(entity);
		mockEntity.setId(lastId);
		lastId++;
		idMap.put(mockEntity.getId(), mockEntity);
		saveToOriginalUrlMap(mockEntity);
		return (S) mockEntity;
	}
	
	private <S extends UrlsLink> S update(S entity) {
		return null;
	}
	
	private void saveToOriginalUrlMap(UrlsLink url) {
		List<UrlsLink> urlList;
		if (originalUrlMap.containsKey(url.getOriginalURL())) {
			urlList = originalUrlMap.get(url.getOriginalURL());
		}else {
			urlList = new ArrayList<UrlsLink>();
			originalUrlMap.put(url.getOriginalURL(), urlList);
		}
		urlList.add(url);
	}
	
	@Override
	public List<UrlsLink> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UrlsLink> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UrlsLink> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UrlsLink> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends UrlsLink> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<UrlsLink> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UrlsLink getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UrlsLink> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UrlsLink> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UrlsLink> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UrlsLink entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends UrlsLink> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends UrlsLink> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UrlsLink> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UrlsLink> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends UrlsLink> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

}
