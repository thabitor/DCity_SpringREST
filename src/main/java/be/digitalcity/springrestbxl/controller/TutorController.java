package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.mapper.TutorMapper;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TutorController {

    private final TutorService service;
    private final TutorMapper mapper;

    public TutorController(TutorService service, TutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/tutor/{id:[0-9]+}")
    public TutorDTO getOne(@PathVariable long id) {
        return mapper.toTutorDto(service.getOne(id));
    }

    @GetMapping("/tutor/all")
    public List<TutorDTO> getAll() {
        return service.getAll().stream()
                .map(mapper::toTutorDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/tutor/new")
    public TutorDTO insert(@RequestBody Tutor tutor) {
       return mapper.toTutorDto(service.save(tutor));
    }
}
