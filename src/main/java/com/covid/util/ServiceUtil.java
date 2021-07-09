package com.covid.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ServiceUtil {
    private ServiceUtil() {}

    private static final List<String> isoCountries = Arrays.asList(Locale.getISOCountries());
    private static final List<String> countries = new ArrayList<>();

    public static boolean isValidISOCountry(String s) {

        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            if (StringUtils.isNotBlank(locale.getDisplayCountry())) {
                countries.add(locale.getDisplayCountry());
                countries.add(locale.getCountry());
            }
        }
        return countries.contains(s);
    }
}
