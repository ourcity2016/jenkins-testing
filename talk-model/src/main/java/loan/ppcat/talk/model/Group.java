package loan.ppcat.talk.model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "talk_group")
public class Group implements Serializable {
    private int id;
    private String name;
    private Conversation conversation;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
