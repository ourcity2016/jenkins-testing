package loan.ppcat.talk.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "talk_groupuser")
public class GroupUser implements Serializable {
    private int id;
    private Group group;
    private User user;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
