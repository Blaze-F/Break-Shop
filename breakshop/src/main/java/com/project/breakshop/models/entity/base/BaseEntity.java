package com.project.breakshop.models.entity.base;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * 상속 엔티티 TimeStamp 전용
*/

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "MODIFIED_DATE", insertable = false, updatable = false)
    @Generated(GenerationTime.ALWAYS)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "CREATED_DATE", insertable = false)
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
