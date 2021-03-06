/*
 *
 * Copyright (c) 2015 Caricah <info@caricah.com>.
 *
 * Caricah licenses this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 *  OF ANY  KIND, either express or implied.  See the License for the specific language
 *  governing permissions and limitations under the License.
 *
 *
 *
 *
 */

package com.caricah.iotracah.datastore.ignitecache.internal;

import com.caricah.iotracah.core.worker.exceptions.DoesNotExistException;
import com.caricah.iotracah.data.IdKeyComposer;
import com.caricah.iotracah.exceptions.UnRetriableException;
import org.apache.commons.configuration.Configuration;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.cache.Cache.Entry;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 * @version 1.0 9/20/15
 */
public abstract class AbstractHandler<T extends IdKeyComposer> implements Serializable {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private String cacheName;
    private IgniteCache<Serializable, T> datastoreCache;

    private IgniteAtomicSequence idSequence;

    private Class<T> classType;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }


    public IgniteCache<Serializable, T> getDatastoreCache() {
        return datastoreCache;
    }

    public void setDatastoreCache(IgniteCache<Serializable, T> datastoreCache) {
        this.datastoreCache = datastoreCache;
    }

    public IgniteAtomicSequence getIdSequence() {
        return idSequence;
    }

    public void setIdSequence(IgniteAtomicSequence idSequence) {
        this.idSequence = idSequence;
    }

    public abstract void configure(Configuration configuration);

    public void initiate(Class<T> t, Ignite ignite) {

        CacheConfiguration clCfg = new CacheConfiguration();

        clCfg.setName(getCacheName());
        clCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        clCfg.setCacheMode(CacheMode.PARTITIONED);

        clCfg = setIndexData(t, clCfg);

        ignite.createCache(clCfg);
        IgniteCache clientIgniteCache = ignite.cache(getCacheName()).withAsync();

        setDatastoreCache(clientIgniteCache);


        classType = t;

        String nameOfSequence = getCacheName()+"-sequence";
        initializeSequence(nameOfSequence, ignite);

    }

    public void initializeSequence(String nameOfSequence , Ignite ignite) {

        long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        IgniteAtomicSequence idSequence = ignite.atomicSequence(nameOfSequence, currentTime, true);
        setIdSequence(idSequence);
    }

    protected CacheConfiguration setIndexData(Class<T> t, CacheConfiguration clCfg) {

        clCfg.setIndexedTypes(String.class, t);
        return clCfg;
    }

    public long nextId(){
        return idSequence.incrementAndGet();
    }

    public Observable<T> getByKey(Serializable key) {

        return Observable.create(observer -> {

                try {
                    // do work on separate thread
                    getDatastoreCache().get(key);
                    IgniteFuture<T> future = getDatastoreCache().future();

                    future.listen(value -> {
                        // callback with value only if not null

                        T actualResult = value.get();
                        if(null != actualResult) {
                            observer.onNext(actualResult);
                            observer.onCompleted();
                        }else{
                            observer.onError(new DoesNotExistException(String.format("%s with key [%s] does not exist.", classType, key)));
                        }

                    });


                } catch (Exception e) {
                    observer.onError(e);
                }

        });

    }


public Observable<T> getByKeyWithDefault(Serializable key, T defaultValue) {

        return Observable.create(observer -> {

            try {
                // do work on separate thread

                getDatastoreCache().get(key);
                IgniteFuture<T> future = getDatastoreCache().future();

                future.listen(f -> {
                    // callback with value only if not null
                    T value = f.get();
                    if (null != value) {
                        observer.onNext(value);
                    }else {
                        observer.onNext(defaultValue);
                    }
                    observer.onCompleted();


                });
            } catch (Exception e) {
                observer.onError(e);
            }

        });

    }



    public Observable<T>  getByQuery(Class<T> t, String query, Object[] params ) {

        return Observable.create(observer -> {

                try {

                    SqlQuery sql = new SqlQuery<Serializable, T>(t, query);
                    sql.setArgs(params);

                    // Find all messages belonging to a client.
                    QueryCursor<Entry<Serializable, T>> queryResult = getDatastoreCache().query(sql);

                    for (Entry<Serializable, T> entry : queryResult) {
                        // callback with value
                        observer.onNext(entry.getValue());
                    }


                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

        });

    }

public <L extends Serializable> Observable<L>  getByQueryAsValue(Class<L> l, String query, Object[] params ) {

        return Observable.create(observer -> {

                try {

                    SqlFieldsQuery sql = new SqlFieldsQuery(query);


                    // Execute the query and obtain the query result cursor.
                    try (QueryCursor<List<?>> queryResult =  getDatastoreCache().query(sql.setArgs(params))) {
                        // callback with value

                        for (List entry : queryResult) {
                            // callback with value
                            observer.onNext((L) entry.get(0));
                        }

                    }

                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

        });

    }

public Observable<List>  getByQueryAsValueList(String query, Object[] params ) {

        return Observable.create(observer -> {

                try {

                    SqlFieldsQuery sql = new SqlFieldsQuery(query);


                    // Execute the query and obtain the query result cursor.
                    try (QueryCursor<?> cursor =  getDatastoreCache().query(sql.setArgs(params))) {
                        // callback with value
                        observer.onNext(cursor.getAll());
                    }

                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

        });

    }


    public void save(T  item) {
            try {
                getDatastoreCache().put(item.generateIdKey(), item);
            } catch (UnRetriableException e) {
                log.error(" save : issues while saving item ", e);
            }

    }

    public void remove(IdKeyComposer item) {

            try {
                getDatastoreCache().remove(item.generateIdKey());
            } catch (UnRetriableException e) {

            }

    }


    public static String preparePlaceHolders(int length) {
        StringBuilder builder = new StringBuilder(length * 2 - 1);
        for (int i = 0; i < length; i++) {
            if (i > 0) builder.append(',');
            builder.append('?');
        }
        return builder.toString();
    }


}
