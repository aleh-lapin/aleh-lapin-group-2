package by.pochem.web.subdomain;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import by.pochem.server.cache.CompaniesCache;

public class SubdomainRequestCondition implements
		RequestCondition<SubdomainRequestCondition> {

	private final Set<String> subdomains;
	private final String tld;
	private final CompaniesCache cache;

	public SubdomainRequestCondition(CompaniesCache cache, String tld, String... subdomains) {
		this(cache, tld, Arrays.asList(subdomains));
	}

	public SubdomainRequestCondition(CompaniesCache cache, String tld, Collection<String> subdomains) {
		this.cache = cache;
		this.subdomains = Collections.unmodifiableSet(new HashSet<String>(subdomains));
		this.tld = tld;
	}

	@Override
	public SubdomainRequestCondition combine(SubdomainRequestCondition other) {
		Set<String> allRoles = new LinkedHashSet<String>(this.subdomains);
		allRoles.addAll(other.subdomains);
		return new SubdomainRequestCondition(cache, tld, allRoles);
	}

	@Override
	public SubdomainRequestCondition getMatchingCondition(HttpServletRequest request) {
		try {
			URL uri = new URL(request.getRequestURL().toString());
			String[] parts = uri.getHost().split(this.tld);
			if (parts.length == 1) {
				for (String s : this.subdomains) {
					if (SubdomainMapping.WILDCARD_COMPANY.equalsIgnoreCase(s)) {
						long id = cache.getCategoryId(parts[0]);
						if (id != 0) {
							request.setAttribute("cacheCompanyId", id);
							return this;
						}
					} else if (s.equalsIgnoreCase(parts[0])) {
						return this;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	@Override
	public int compareTo(SubdomainRequestCondition other, HttpServletRequest request) {
		return CollectionUtils.removeAll(other.subdomains, this.subdomains).size();
	}

}
