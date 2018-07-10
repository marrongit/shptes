package com.example.maguilar.shptes;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by maguilar on 26/06/2018.
 */

public class MyApplication extends Application{
    public static AtomicInteger UserId = new AtomicInteger();
    public static AtomicInteger ShirtId = new AtomicInteger();
    public static AtomicInteger PoloId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        setUpConfiguration();
        Realm realm = Realm.getDefaultInstance();
        UserId = getIdByTable(realm,Users.class);
        ShirtId = getIdByTable(realm,Shirts.class);
        PoloId = getIdByTable(realm,Polos.class);
        realm.close();
    }

    public void setUpConfiguration(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> realmResults = realm.where(anyClass).findAll();
        return realmResults.size() > 0 ? new AtomicInteger(realmResults.max("id").intValue()) : new AtomicInteger();
    }
}
