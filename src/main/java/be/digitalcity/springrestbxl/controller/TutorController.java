package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.mapper.TutorMapper;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.forms.TutorForm;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tutor")

public class TutorController {

    private final TutorService service;
    private final TutorMapper mapper;


    public TutorController(TutorService service, TutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id:[0-9]+}")
    public TutorDTO getOne(@PathVariable long id) {
        return service.getOne(id);
    }

    @GetMapping({"", "/all"})
    public List<TutorDTO> getAll() {
        return service.getAll();
    }


    @PostMapping("/insert")
    public TutorDTO insert(@RequestBody TutorForm form){
        return service.create(form);
    }

    @DeleteMapping("/{id}")
    public TutorDTO delete(@PathVariable long id){
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public TutorDTO update(@PathVariable long id, @Valid @RequestBody TutorForm form ){
        return service.update(id, form);

    }

    @GetMapping(params = "city")
    public List<TutorDTO> getAllFromVille(@RequestParam String ville){
        return service.getAllFromCityWithChild(ville);
    }
}
