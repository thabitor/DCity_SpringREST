package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.mapper.ChildMapper;
import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.dto.ReservationDTO;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import be.digitalcity.springrestbxl.service.ChildService;
import be.digitalcity.springrestbxl.service.ReservationService;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {

    private final ChildService service;
    private final ReservationService reservationService;


    public ChildController(ChildService service, ReservationService reservationService) {
        this.service = service;
        this.reservationService = reservationService;
    }

    @PostMapping("/insert")
    public ChildDTO insert(@Valid @RequestBody ChildInsertForm form){
        return service.create(form);
    }

    @GetMapping("/{id:[0-9]+}")
    public ChildDTO getOne(@PathVariable long id){
        return service.getOne(id);
    }

    @GetMapping ({"", "/all"})
    public List<ChildDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChildDTO delete(@PathVariable long id){
       return service.delete(id);
    }

    @PutMapping("/{id}")
    public ChildDTO update(@PathVariable long id, @Valid @RequestBody ChildUpdateForm form ){
        return service.update(id, form);
    }

    @PatchMapping("/{id:[0-9]+}")
    public ChildDTO patchTutors(@PathVariable long id, @Valid @RequestBody Collection<Long> tutorIds){
        return service.changeTutors(id, tutorIds);
    }

    @GetMapping(value = "/allergy")
    public List<ChildDTO> getAllWithAllergie(@RequestParam String allergy){
        return service.getAllWithAllergy(allergy);
    }
    // GET http://localhost:8080/enfant/{id}/reservation/future
    @GetMapping("/{id:[0-9]+}/reservation/future")
    public List<ReservationDTO> futureReservation(@PathVariable Long id){
        return reservationService.futureReservOfChild(id);
    }

    @GetMapping(value = "/on-day", params = "date")// 30-12-2020 : pattern= "dd-MM-yyyy"
    public List<ChildDTO> presentOnDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date){
        return service.getAllPresentOnDay(date);
    }


}
