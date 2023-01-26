package com.project.breakshop.models.entity.base;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * TimeStamp 전용 상속 엔티티
*/

@MappedSuperclass
@Data //TODO 필요한지 나중에 알아보기
public class BaseEntity {

    @Generated(GenerationTime.ALWAYS)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE", updatable = false, insertable = false)
    private Date modifiedDate;

    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", insertable = false)
    private Date createdDate;

}
