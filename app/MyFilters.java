import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;
import javax.inject.Inject;

/**
 * Created by Panagiotis Nikolaropoulos on 10/10/2016.
 */
public class MyFilters extends DefaultHttpFilters {

    @Inject public MyFilters(CORSFilter corsFilter) {
        super(corsFilter);
    }
}