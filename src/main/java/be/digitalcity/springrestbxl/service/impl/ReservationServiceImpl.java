package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.exceptions.ElementNotFoundException;
import be.digitalcity.springrestbxl.exceptions.FormValidationException;
import be.digitalcity.springrestbxl.exceptions.InvalidReferenceException;
import be.digitalcity.springrestbxl.exceptions.UnavailableDatesException;
import be.digitalcity.springrestbxl.mapper.ReservationMapper;
import be.digitalcity.springrestbxl.model.dto.ReservationDTO;
import be.digitalcity.springrestbxl.model.entities.Reservation;
import be.digitalcity.springrestbxl.model.forms.ReservationCancellationForm;
import be.digitalcity.springrestbxl.model.forms.ReservationRequestForm;
import be.digitalcity.springrestbxl.repository.ChildRepository;
import be.digitalcity.springrestbxl.repository.ReservationRepository;
import be.digitalcity.springrestbxl.repository.TutorRepository;
import be.digitalcity.springrestbxl.service.ReservationService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired // Pas recommendé
    private ReservationRepository repository;
    @Autowired // a préférer: injection par constructeur
    private ReservationMapper mapper;
    @Autowired
    private ChildRepository enfantRepository;
    @Autowired
    private TutorRepository tutorRepository;

    public ReservationServiceImpl(ReservationRepository repository, ReservationMapper mapper, ChildRepository enfantRepository, TutorRepository tutorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.enfantRepository = enfantRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public ReservationDTO requestReservation(@NonNull ReservationRequestForm form) {

        if( !dateAvailable(form.getArrive(), form.getDepart()) )
            throw new UnavailableDatesException(form.getArrive(), form.getDepart());

        Reservation reservation = mapper.toEntity(form);

        reservation.setTutorDepose(
                tutorRepository.findById(form.getTutorDepot())
                        .orElseThrow(() -> new InvalidReferenceException(List.of(form.getTutorDepot())))
        );

        reservation.setTutorReprend(
                tutorRepository.findById(form.getTutorDepart())
                        .orElseThrow(() -> new InvalidReferenceException(List.of(form.getTutorDepart())))
        );

        reservation.setChild(
                enfantRepository.findById(form.getChildId())
                        .orElseThrow(() -> new InvalidReferenceException(List.of(form.getChildId())))
        );

        return mapper.toDto( repository.save(reservation) );
    }

    @Override
    public boolean dateAvailable(LocalDateTime arrive, LocalDateTime depart) {
        MultiValueMap<String,String> messages = null;
        if( arrive.isAfter(depart) ){
            messages = new LinkedMultiValueMap<>();
            messages.add("arrival/departure", "le moment d'arrive doit être avant le moment de depart");
        }

        if( !arrive.toLocalDate().equals( depart.toLocalDate() ) ){
            messages = messages == null ? new LinkedMultiValueMap<>() : messages;
            messages.add("arrival/departure", "les deux moment doivent correspondre au même jour");
        }

        LocalDateTime endOfThisDay = LocalDate.now().atTime(23,59,59);
        if( arrive.isBefore( endOfThisDay ) ){
            messages = messages == null ? new LinkedMultiValueMap<>() : messages;
            messages.add("arrival", "devrait être future");
        }

        if( depart.isBefore( endOfThisDay ) ){
            messages = messages == null ? new LinkedMultiValueMap<>() : messages;
            messages.add("arrival", "devrait être future");
        }

        // Attention au max 10heures et 59 secondes en realité :/
        double hoursStayed = Duration.between(arrive, depart).toMinutes() / 60.;
        if( hoursStayed < 1 || hoursStayed > 10 ){
            messages = messages == null ? new LinkedMultiValueMap<>() : messages;
            messages.add("arrival/departure", "temps d'accueil entre 1 et 10 heures");
        }

        if( messages != null )
            throw new FormValidationException(messages);

        return repository.checkAvailable(arrive, depart);
    }

    @Override
    public void cancelReservation(@Valid ReservationCancellationForm form) {
        if( !repository.existsById(form.getId()) )
            throw new ElementNotFoundException(Reservation.class, form.getId());

        repository.cancelReservation(form.getId(), form.getMotif());
    }

    @Override
    public List<ReservationDTO> futureReservOfChild(Long childId) {
        return repository.futurReservOfChild(childId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ReservationDTO> futureOfCurrentMonth() {
        // Alternative 1
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime lastDayOfThisMonth = LocalDateTime.of(
//                now.getYear(),
//                now.getMonth(),
//                now.getMonth().length(now.toLocalDate().isLeapYear()),
//                23,
//                59,
//                59
//        );
//        return repository.findByArriveAfterAndDepartBeforeAndAnnuleFalse(now, lastDayOfThisMonth).stream()
//                .map(mapper::toDto)
//                .toList();

        // Alternative 2
        return repository.findForCurrentMonth().stream()
                .map(mapper::toDto)
                .toList();
    }
}