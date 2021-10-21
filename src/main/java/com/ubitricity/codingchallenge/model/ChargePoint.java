package com.ubitricity.codingchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargePoint {
    @Id
    private Long id;
    @Enumerated(STRING)
    private Status status;
    @Enumerated(STRING)
    private ChargeType chargeType;
    @Column
    private Date lastPlugged;

    public enum Status {
        OCCUPIED, AVAILABLE
    }

    public enum ChargeType {
        FAST(20),
        SLOW(10),
        OFF(0);

        private final int amperes;

        ChargeType(int amperes) {
            this.amperes = amperes;
        }

        public int getAmperes() {
            return amperes;
        }
    }
}

