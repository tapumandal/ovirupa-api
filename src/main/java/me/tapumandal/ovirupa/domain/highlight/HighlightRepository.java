package me.tapumandal.ovirupa.domain.highlight;

import org.hibernate.Session;

public interface HighlightRepository {

    public Session getSession();
    public Highlight create(Highlight highlight);
    public Highlight update(Highlight highlight);
    public Highlight gethighlight();
}
