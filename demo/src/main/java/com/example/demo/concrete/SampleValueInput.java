package com.example.demo.concrete;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NotNull
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
public class SampleValueInput implements Serializable {

    private LocalDate date;

    private String indexCode;
    private BigDecimal indexValue;
    private String indexType;

}
