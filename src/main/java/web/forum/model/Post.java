package web.forum.model;

import javax.persistence.*;
import java.util.*;

/**
 * Модель данных описывающая - Сообщение.
 */
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Calendar created;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Set<Discussion> discussions = new HashSet<>();

    public static Post of(String name, String description) {
        Post post = new Post();
        post.created = new GregorianCalendar();
        post.name = name;
        post.description = description;
        post.created.setTime(post.created.getTime());
        return post;
    }

    public static Post of(int id, String name, String description) {
        Post post = new Post();
        post.created = new GregorianCalendar();
        post.id = id;
        post.name = name;
        post.description = description;
        post.created.setTime(post.created.getTime());
        return post;
    }

    public static Post of(int id, String name, String description, Calendar created, User user) {
        Post post = new Post();
        post.created = created;
        post.id = id;
        post.name = name;
        post.description = description;
        post.user = user;
        return post;
    }

    public void addDiscussion(Discussion discussion) {
        this.discussions.add(discussion);
    }

    public void addUserToPost(User user) {
        this.user = user;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(Set<Discussion> discussions) {
        this.discussions = discussions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", user=" + user
                + ", discussions=" + discussions
                + '}';
    }
}
