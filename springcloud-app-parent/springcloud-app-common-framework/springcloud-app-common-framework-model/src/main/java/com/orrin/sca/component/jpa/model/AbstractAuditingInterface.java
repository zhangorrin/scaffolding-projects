package com.orrin.sca.component.jpa.model;

import java.io.Serializable;

public interface AbstractAuditingInterface extends Serializable {
    public String getCreatedBy();

    public String getCreatedDate();

    public String getLastModifiedBy();

    public String getLastModifiedDate();

}
