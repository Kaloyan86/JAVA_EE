package app.web.beans;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;
import app.domain.models.view.FriendsViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FriendBean extends BaseBean {


    private List<FriendsViewModel> friends;

    private UserService userService;
    private ModelMapper modelMapper;

    public FriendBean() {
    }

    @Inject
    public FriendBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        String id = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).getAttribute("userId");

        this.setFriends(this.userService.getById(id)
                .getFriends().stream()
                .map(f -> this.modelMapper.map(f, FriendsViewModel.class)).collect(Collectors.toList()));
    }

    public List<FriendsViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsViewModel> friends) {
        this.friends = friends;
    }

    public void unfriend(String unfId) {

        String id = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).getAttribute("userId");

        this.userService.remove(id,unfId);

        this.redirect("/friends");
    }

}
