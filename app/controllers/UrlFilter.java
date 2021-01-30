package controllers;




import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class UrlFilter extends DefaultHttpFilters {
    @Inject public UrlFilter(CORSFilter corsFilter) {
        super(corsFilter);
    }
}