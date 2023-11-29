package com.makogon.foodtracker.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userdetails")
public class UserDetails {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_detailsid;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Person person;
    private float height;
    private float weight;
    private int age;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityid")
    private Activity activity;
    private String sex;

    public UserDetails(long user_detailsid){
        this.user_detailsid =user_detailsid;
    }
}
//не генерится айдишка
