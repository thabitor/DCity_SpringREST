package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.mapper.TutorMapper;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.TutorInsertForm;
import be.digitalcity.springrestbxl.model.forms.TutorUpdateForm;
import be.digitalcity.springrestbxl.service.ChildService;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TutorController {

    private final TutorService service;
    private final TutorMapper mapper;

    private final ChildService childService;

    public TutorController(TutorService service, TutorMapper mapper, ChildService childService) {
        this.service = service;
        this.mapper = mapper;
        this.childService = childService;
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
    public TutorDTO newTutor(@RequestBody Tutor tutor) {
       return mapper.toTutorDto(service.save(tutor));
    }

    @PostMapping("/tutor/insert")
    public TutorDTO insert(@RequestBody TutorInsertForm form){
        Tutor entity = mapper.toEntity(form);
        entity = service.save( entity );
        return mapper.toTutorDto( entity );
    }

    @DeleteMapping("/tutor/delete/{id}")
    public TutorDTO delete(@PathVariable long id){
        return mapper.toTutorDto( service.delete(id) );
    }

    @PutMapping("/tutor/update/{id}")
    public TutorDTO update(@PathVariable long id, @RequestBody TutorUpdateForm form ){

        Tutor entity = mapper.toEntity(form);
        return mapper.toTutorDto( service.update( id, entity ) );

    }
}
