package com.project.breakshop.models.DTO;

import com.project.breakshop.models.entity.MenuOption;
import lombok.*;

import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MenuDTO {

    private String name;
    private int price;
    private Lob photo; //TODO
    private String description;


}
