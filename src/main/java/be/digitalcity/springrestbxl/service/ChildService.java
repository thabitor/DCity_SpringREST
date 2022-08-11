package be.digitalcity.springrestbxl.service;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;

import java.util.Collection;
import java.util.List;

public interface ChildService extends CrudService<ChildDTO, Long, ChildInsertForm, ChildUpdateForm> {

    ChildDTO changeTutors(Long id, Collection<Long> idTutors);

    List<ChildDTO> getAllWithAllergy(String allergy);

}
