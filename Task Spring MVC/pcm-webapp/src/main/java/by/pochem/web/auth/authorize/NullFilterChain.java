package by.pochem.web.auth.authorize;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class NullFilterChain implements FilterChain {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response) {
    throw new UnsupportedOperationException("doFilter()");
  }
}
