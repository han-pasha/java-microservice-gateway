package com.training.javaexercise.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {

    @Id
    private Long id;
    private String name;
    private Integer channelNumber;

}
