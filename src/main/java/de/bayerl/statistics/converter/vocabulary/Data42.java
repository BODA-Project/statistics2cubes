package de.bayerl.statistics.converter.vocabulary;

public class Data42 extends LocalNS{

	protected static final String PREFIX = "data42";
    protected static final String URI = "http://42-data.org/resource/";

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getURI() {
        return URI;
    }
}
