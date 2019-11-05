package app.web.beans;

import app.domain.models.service.UserServiceModel;
import app.domain.models.view.HomeViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class HomeBean extends BaseBean {

    private List<HomeViewModel> models;

    private UserService userService;
    private ModelMapper modelMapper;

    public HomeBean() {
    }

    @Inject
    public HomeBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        String username = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).getAttribute("username");

        System.out.println();

        this.setModels(this.userService.getAll()
                .stream()
                .filter(u -> !u.getUsername().equals(username) &&
                        !u.getFriends().stream()
                                .map(UserServiceModel::getUsername)
                                .collect(Collectors.toList()).contains(username))
                .map(u -> this.modelMapper.map(u, HomeViewModel.class))
                .collect(Collectors.toList()));
    }

    public void addFriend(String friendId){

        String id = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).getAttribute("userId");

        UserServiceModel loggedIn = this.userService.getById(id);
        UserServiceModel friend = this.userService.getById(friendId);

        loggedIn.getFriends().add(friend);
        friend.getFriends().add(loggedIn);

        this.userService.addFriend(loggedIn);
        this.userService.addFriend(friend);

        redirect("/home");
    }

    public List<HomeViewModel> getModels() {
        return models;
    }

    public void setModels(List<HomeViewModel> models) {
        this.models = models;
    }
}
