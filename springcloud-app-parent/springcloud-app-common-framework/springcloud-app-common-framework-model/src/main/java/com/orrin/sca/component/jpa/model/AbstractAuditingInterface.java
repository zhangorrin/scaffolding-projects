package com.orrin.sca.component.jpa.model;

import org.joda.time.DateTime;

import java.io.Serializable;

public interface AbstractAuditingInterface extends Serializable {
    public String getCreatedBy();

    public DateTime getCreatedDate();

    public String getLastModifiedBy();

    public DateTime getLastModifiedDate();

}
