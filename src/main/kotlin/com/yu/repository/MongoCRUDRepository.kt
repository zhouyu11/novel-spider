package com.yu.repository

import com.mongodb.ReadPreference
import com.yu.domain.model.MongoEntity
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.UpdateOptions
import org.mongodb.morphia.query.FindOptions
import org.mongodb.morphia.query.Query
import org.mongodb.morphia.query.UpdateOperations

import java.util.Arrays

/**
 * Created by zhouyu on 07/06/2017.
 */
open class MongoCRUDRepository {
    val client: Datastore = MongoClientInstance.instance()

    init {
        FIND_PRIMARY.readPreference(ReadPreference.primary())
        FIND_SECONDARY.readPreference(ReadPreference.secondaryPreferred())
    }

    fun <T> update(query: Query<T>, updateOperations: UpdateOperations<T>) {
        client.update(query, updateOperations)
    }

    fun <T> update(query: Query<T>, updateOperations: UpdateOperations<T>, updateOptions: UpdateOptions) {
        client.update(query, updateOperations, updateOptions)
    }

    fun update(entity: MongoEntity) {
        client.merge(entity)
    }

    fun save(entity: MongoEntity) {
        client.save(entity)
    }

    fun saveMany(vararg entities: MongoEntity) {
        client.save(Arrays.asList(*entities))
    }

    fun saveMany(entities: List<MongoEntity>) {
        client.save(entities)
    }

    fun delete(entity: MongoEntity) {
        client.delete(entity)
    }


    companion object {
        private val FIND_PRIMARY = FindOptions()
        private val FIND_SECONDARY = FindOptions()
    }
}
