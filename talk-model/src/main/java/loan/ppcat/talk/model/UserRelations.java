package loan.ppcat.talk.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "talk_userrelations")
public class UserRelations implements Serializable {
    private int id;
    private User self;
    private User friend;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    @ManyToOne
    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
