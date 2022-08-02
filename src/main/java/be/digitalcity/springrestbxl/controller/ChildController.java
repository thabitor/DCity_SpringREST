package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.mapper.ChildMapper;
import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import be.digitalcity.springrestbxl.service.ChildService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChildController {

    private final ChildService service;
    private final ChildMapper mapper;

    public ChildController(ChildService service, ChildMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/child/{id:[0-9]+}")
    public ChildDTO getOne(@PathVariable long id){
        return mapper
                .toChildDto(service.getOne(id));
    }

    @GetMapping ("/child/all")
    public List<ChildDTO> getAll() {
        return service.getAll().stream()
                .map(mapper::toChildDto)
                .collect(Collectors.toList());
    }

    @PostMapping ("/child/new")
    public ChildDTO insert(@RequestBody Child child) {

        return mapper.toChildDto(service.save(child));
    }

    @PostMapping
    public ChildDTO insert(@RequestBody ChildInsertForm form){
        Child entity = mapper.toEntity(form);
        entity = service.save( entity );
        return mapper.toChildDto( entity );
    }

    @DeleteMapping("/{id}")
    public ChildDTO delete(@PathVariable long id){
        return mapper.toChildDto( service.delete(id) );
    }

    @PutMapping("/{id}")
    public ChildDTO update(@PathVariable long id, @RequestBody ChildUpdateForm form ){

        Child entity = mapper.toEntity(form);
        return mapper.toChildDto( service.update( id, entity ) );

    }


}
