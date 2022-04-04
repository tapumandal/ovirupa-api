package me.tapumandal.ovirupa.domain.highlight;

import me.tapumandal.ovirupa.service.Service;

import java.util.List;

public interface HighlightService extends Service<HighlightDto, Highlight> {
    public List<Highlight> gethighlight();
}
