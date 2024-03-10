package pl.zeto.backend.VMC.controller;

import org.springframework.web.bind.annotation.*;
import pl.zeto.backend.VMC.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/Search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<String> search(@RequestParam String query) {
        return searchService.search(query);
    }
}