package com.yu.domain.model

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

/**
 * Created by zhouyu on 07/06/2017.
 */

@Entity(value = "novels", noClassnameStored = true)
class Novel : MongoEntity() {
    @Id
    var id = ObjectId()

    var name: String? = null

    var author: String? = null

    var description: String? = null

    var link: String? = null

    var host: String? = null

    var status: NovelStatus? = null

    var latestChapterNumber: Int? = null

    var latestChapterName: String? = null

}
