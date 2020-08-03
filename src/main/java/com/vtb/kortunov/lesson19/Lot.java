package com.vtb.kortunov.lesson19;

import javax.persistence.*;

@Entity
@Table(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer currentRate;

    @ManyToOne
    @JoinColumn(name = "owner_last_bet")
    private User ownerLastBet;

    @Version
    Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(Integer currentRate) {
        this.currentRate = currentRate;
    }

    public User getOwnerLastBet() {
        return ownerLastBet;
    }

    public void setOwnerLastBet(User ownerLastBet) {
        this.ownerLastBet = ownerLastBet;
    }

    public Lot() {
    }
}
