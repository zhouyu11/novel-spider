package com.yu.repository

import com.yu.domain.model.Novel
import org.springframework.stereotype.Component

/**
 * Created by zhouyu on 07/06/2017.
 */
@Component
open class NovelDao : MongoCRUDRepository() {
    open fun list(): List<Novel> {
        val query = client.createQuery(Novel::class.java)
        return query.asList()
    }
}
