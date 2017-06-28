package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.model.Role;
import br.edu.unigranrio.ect.si.cfa.service.RoleService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;
import br.edu.unigranrio.ect.si.cfa.web.annotation.LoggedRoleId;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ConversationScoped
public class RoleAction extends ConversationAction<Role, Long> {

    private static final long serialVersionUID = 5888344693047818484L;

    @Inject @LoggedRoleId
    private Long loggedRoleId;

    @Inject
    private RoleService service;

    private List<Feature> selectedFeatures;

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected Role newInstance() {
        return new Role();
    }

    @Override
    protected Long parseId(String id) {
        return Long.parseLong(id);
    }

    public List<Feature> getFeatures() {
        return service().list(Feature.class);
    }

    @Override
    @Transactional
    public String save() {
        if (!getInstance().isIdNotNull()) {
            getInstance().setId(null);
            service().save(getInstance());
        }
        getInstance().setFeatures(selectedFeatures);
        service().update(getInstance());
        return back();
    }

    @Override
    public String edit() {
        String result = super.edit();
        List<Feature> features = getInstance().getFeatures();
        if (features != null && !features.isEmpty()) {
            this.selectedFeatures = new ArrayList<>(features.size());
            this.selectedFeatures.addAll(features);
        }
        return result;
    }

    @Override
    public List<Role> getInstances() {
        return super.getInstances().stream()
                .filter(ro -> !ro.getId().equals(loggedRoleId))
                .filter(ro -> !ro.getId().equals(Constants.ROLE_ADMIN_ID))
                .collect(Collectors.toList());
    }

    public List<Feature> getSelectedFeatures() {
        return selectedFeatures;
    }

    public void setSelectedFeatures(List<Feature> selectedFeatures) {
        this.selectedFeatures = selectedFeatures;
    }
}
