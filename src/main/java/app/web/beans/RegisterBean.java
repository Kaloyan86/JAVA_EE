package app.web.beans;

import app.domain.models.binding.RegisterBindingModel;
import app.domain.models.service.UserServiceModel;
import app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterBean extends BaseBean {

    private RegisterBindingModel model;

    private UserService userService;
    private ModelMapper modelMapper;

    public RegisterBean() {
    }

    @Inject
    public RegisterBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    void init() {
        this.model = new RegisterBindingModel();
    }

    public void register() {

        if (!this.model.getPassword().equals(this.model.getConfirmPassword())) {
            this.redirect("/register");
        }
        this.model.setPassword(DigestUtils.sha256Hex(this.model.getPassword()));
        this.userService.register(this.modelMapper.map(this.model, UserServiceModel.class));
        this.redirect("/login");
    }

    public RegisterBindingModel getModel() {
        return model;
    }

    public void setModel(RegisterBindingModel model) {
        this.model = model;
    }
}
