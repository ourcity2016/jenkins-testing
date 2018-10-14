package loan.ppcat.talk.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "talk_conversation")
public class Conversation implements Serializable {
    private int id;
    private byte type;
    private String queueKey;
//    private Group group;
//    private User user;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getQueueKey() {
        return queueKey;
    }

    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }

//    @OneToOne
//    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }
//
//    @OneToOne
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
