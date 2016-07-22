package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT um FROM UserMeal um WHERE um.user.id=:userId ORDER BY um.dateTime DESC "),
        @NamedQuery(name = UserMeal.GET, query = "SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET_BETWEEN_DATES, query = "SELECT um FROM UserMeal um WHERE um.user.id=:userId AND um.dateTime BETWEEN :startData AND :endData")
})

@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String GET = "UserMeal.get";
    public static final String GET_BETWEEN_DATES = "UserMeal.getBetweenDates";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @Column(name = "calories", nullable = false)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
