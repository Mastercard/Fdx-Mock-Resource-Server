package com.mastercard.fdx.mock.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestMethodUri {

	private String method;
	private String uri;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			RequestMethodUri other = (RequestMethodUri) obj;
			return (method.equals(other.method) && uriMatches(other.uri, uri));
		}
		return false;
	}

	private boolean uriMatches(String mapUri, String reqUri) {
		if (mapUri.endsWith("**")) return reqUri.startsWith(mapUri.replace("**", ""));
		else {
			return reqUri.startsWith(mapUri);
		}
	}

}
