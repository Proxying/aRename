package uk.co.proxying.aRename.utils;

import uk.co.proxying.aRename.Arename;

/**
 * Created by Kieran Quigley (Proxying) on 28-May-16 for CherryIO.
 */
public class Config<T> {

    T t;
    private String property;

    public Config(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) Arename.getInstance().getConfig().get(property);
    }
}
