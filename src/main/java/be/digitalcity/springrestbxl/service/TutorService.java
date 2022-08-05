package be.digitalcity.springrestbxl.service;

import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.forms.TutorForm;

import java.util.Collection;
import java.util.Set;

public interface TutorService extends CrudService<TutorDTO, Long, TutorForm, TutorForm>  {

    Set<TutorDTO> getAllById(Collection<Long> ids);

}
