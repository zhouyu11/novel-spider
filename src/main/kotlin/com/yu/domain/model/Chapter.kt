package com.yu.domain.model

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.*

/**
 * Created by zhouyu on 07/06/2017.
 */

@Entity(value = "chapter", noClassnameStored = true)
@Indexes(Index(fields = arrayOf(Field("novelName"), Field("name")), options = IndexOptions(background = true)))
class Chapter : MongoEntity() {
    @Id
    var id = ObjectId()

    @Reference
    var novel: Novel? = null

    var name: String? = null

    var number: Int? = null

    var content: String? = null

    var link: String? = null

}
