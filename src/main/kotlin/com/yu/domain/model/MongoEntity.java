package com.yu.domain.model;

import org.mongodb.morphia.annotations.PrePersist;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhouyu on 07/06/2017.
 */
public abstract class MongoEntity implements Serializable {

    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    public void onCreateOrUpdate() {
        // Insert
        if (this.createdAt == null) {
            this.createdAt = new Date();
            this.updatedAt = this.createdAt;
        }
        // Update
        else {
            this.updatedAt = new Date();
        }
    }
}
