package app.web.beans;

import app.domain.models.view.HomeViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class ProfileBean extends BaseBean {

    private HomeViewModel homeViewModel;

    private UserService userService;
    private ModelMapper modelMapper;

    public ProfileBean() {
    }

    @Inject
    public ProfileBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {

        String id = ((HttpServletRequest)
                FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("id");

        this.homeViewModel = this.modelMapper.map(this.userService.getById(id), HomeViewModel.class);
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }

    public void setHomeViewModel(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }
}
